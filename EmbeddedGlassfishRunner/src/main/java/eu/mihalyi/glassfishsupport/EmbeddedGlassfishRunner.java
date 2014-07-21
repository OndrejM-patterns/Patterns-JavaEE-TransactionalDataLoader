package eu.mihalyi.glassfishsupport;

import org.glassfish.embeddable.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class EmbeddedGlassfishRunner {

 private EmbeddedGlassfishRunnerArguments settings;
 private AtomicBoolean initialized = new AtomicBoolean();
 private GlassFish glassfish;

 public EmbeddedGlassfishRunner(EmbeddedGlassfishRunnerArguments arguments) {
   this.settings = arguments;
 }

 public EmbeddedGlassfishRunner init() throws Exception{
   if ( initialized.get() ) {
     throw new RuntimeException("runner was already initialized");
   }
   BootstrapProperties bootstrapProperties = new BootstrapProperties();
   GlassFishRuntime glassfishRuntime = GlassFishRuntime.bootstrap(bootstrapProperties);

   GlassFishProperties glassfishProperties = new GlassFishProperties();
   glassfishProperties.setPort("http-listener", settings.port);
   String [] paths = System.getProperty("java.class.path").split(File.pathSeparator);
   for (int j=0; j<paths.length; ++j) {
     System.out.printf("classpath[%d] = %s\n", j, paths[j]);
   }
   glassfish = glassfishRuntime.newGlassFish(glassfishProperties);
   initialized.set(true);
   return this;
 }

  private void check() {
    if ( !initialized.get() ) {
      throw new RuntimeException("runner was not initialised");
    }
  }

  public EmbeddedGlassfishRunner start() throws Exception{
    check();
    glassfish.start();
    if (settings.deployable != null) {
        glassfish.getDeployer().deploy(new File(settings.deployable));
    }
    return this;
  }

  public EmbeddedGlassfishRunner stop() throws Exception{
    check();
    glassfish.stop();
    return this;
  }

  public static void main(String args[]) throws Exception {
    EmbeddedGlassfishRunner runner = new EmbeddedGlassfishRunner(EmbeddedGlassfishRunnerArguments.fromCommandLine(args)).init().start();
    Thread.sleep(1000);
    runner.stop();
  }
  
}
