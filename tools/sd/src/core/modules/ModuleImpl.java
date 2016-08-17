package core.modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Logger;
import common.Utils;

import core.beans.Strings.Common;
import core.interfaces.IModule;

public abstract class ModuleImpl implements IModule {

	protected Process process = null;

	protected Map<String, String> options = new HashMap<String, String>();

	public void setValue(String key, String value) {
		this.options.put(key, value);
	}

	public String getValue(String key) {
		return this.options.get(key);
	}

	public void init() {

	}

	public List<String> run() {

		ProcessBuilder command = new ProcessBuilder();
		List<String> cmd = new ArrayList<String>();
		options.put(Common.OUTPUT, Utils.getOutputFile());
		cmd.add(options.get(Common.EXEC));
		cmd.add("--potfile-disable");
		cmd.add("-m");
		cmd.add(options.get(Common.MODULE));
		cmd.add("-o");
		cmd.add(options.get(Common.OUTPUT));
		if (options.containsKey(Common.RULES)) {
			cmd.add("-r");
			cmd.add(options.get(Common.RULES));
		}

		cmd.add(options.get(Common.INPUT));
		cmd.add(options.get(Common.TAIL));
		System.out.println("Command" + cmd.toString());

		try {
			command = new ProcessBuilder(cmd);

			process = command.start();
			BufferedReader stdOut = new BufferedReader(new InputStreamReader(
					process.getInputStream()));

			while ((stdOut.readLine()) != null) {

				try {
					process.exitValue();
					break;
				} catch (Exception e) {
				}

			}
		} catch (IOException e) {

			Logger.error(e.getMessage());
		}
		List<String> ret = Utils.readFile(options.get(Common.OUTPUT));
		Utils.deleteFile(options.get(Common.OUTPUT));

		return ret;
	}
}
