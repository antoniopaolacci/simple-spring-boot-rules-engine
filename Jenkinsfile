pipeline {
  agent any
  stages {

	stage ('Initialize') {
      steps {
        echo 'Placeholder.'
      }
    }

	stage('Build') {
        steps {
           script {
                def os = System.properties['os.name'].toLowerCase()
                echo "OS: ${os}"                
                if (os.contains("linux")) {
                    sh "mvn install -DskipTests" 
                } else {
                    bat "mvn install -DskipTests"
               }
          }
       }
   }

  }
}