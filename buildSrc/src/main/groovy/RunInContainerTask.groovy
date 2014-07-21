import org.gradle.api.tasks.JavaExec

// runs an artifact in embedded glassfish server

class RunInContainerTask extends JavaExec {
    
    def port = null
    def deployable = null
    
    RunInContainerTask() {
        this.main = 'eu.mihalyi.glassfishsupport.EmbeddedGlassfishRunner'
        this.classpath = this.project.sourceSets.main.runtimeClasspath
    }
    
    void exec() {
        if (this.port != null) {
            args "port=${this.port}"
        }
        if (this.deployable != null) {
            args "deployable=${this.deployable}"
        }
        println args
        super.exec()
    }
}