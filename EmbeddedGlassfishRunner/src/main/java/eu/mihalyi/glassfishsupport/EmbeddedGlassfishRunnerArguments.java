/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.mihalyi.glassfishsupport;

/**
 *
 * @author ondrej.mihalyi
 */
public class EmbeddedGlassfishRunnerArguments {
    int port = 8080;
    String deployable = null;
    static EmbeddedGlassfishRunnerArguments fromCommandLine(String[] args) {
        EmbeddedGlassfishRunnerArguments a = new EmbeddedGlassfishRunnerArguments();
        for (String arg : args) {
            String[] argParts = arg.split("=", 2);
            String argName = argParts[0];
            String argValue = argParts[1];
            switch (argName) {
                case "port":
                    a.port = Integer.parseInt(argValue);
                    break;
                case "deployable":
                    a.deployable = argValue;
                    break;
            }
        }
        return a;
    }
}
