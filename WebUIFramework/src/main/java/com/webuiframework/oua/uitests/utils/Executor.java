package com.webuiframework.oua.uitests.utils;


import static com.webuiframework.oua.uitests.utils.ConvertUtil.arrayToString;

public final class Executor {

    private Executor(){}
    /**
     *   Executes the specified string command with parameters in a separate process.
     *
     *   @param path - path where file for execution is located
     *   @param command - command file for execution (ex.: cmd.exe)
     *   @param parameters - parameters for command
     *
     *   @return the exit value for the subprocess.
     */
    public static int runExec( String path, String command, String[] parameters){
        String parameter = arrayToString(parameters, " ");
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(path + command + " " + parameter);
            pr.waitFor();
            return pr.exitValue();

        } catch (Exception e) {
            return -1;
        }
    }
    

    /**
     *   Executes the specified string command in a separate process.
     *
     *   @param path - path where file for execution is located
     *   @param command - command file for execution (ex.: cmd.exe)
     *
     *   @return the exit value for the subprocess.
     */
    public static int runExec(String path, String command ){
        String[] parameters = {" "};
        return runExec(path, command, parameters);
    }

}

