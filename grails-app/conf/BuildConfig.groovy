import grails.util.Environment

grails.project.work.dir = "target"

def environment = Environment.getCurrent()

if (appName != 'iceScrum-plugin-planning-poker' && environment != Environment.PRODUCTION){
    grails.plugin.location.'icescrum-core' = '../icescrum-core'
}

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()
        mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
        mavenRepo "http://repo.icescrum.org/artifactory/plugins-snapShots/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.13'
    }

    plugins {
        if (appName == 'iceScrum-plugin-planning-poker' || environment == Environment.PRODUCTION){
            compile "org.icescrum:icescrum-core:1.6-SNAPSHOT"
        }
        compile(':maven-publisher:0.8.1'){
            export:false
        }
    }
}