pipeline {
  agent any
  stages {

	stage ('Initialize') {
      steps {
        echo 'Placeholder.'
      }
    }
    
    stage ('Checking java version') {
      steps {
         sh 'java -version'
       }
     }
        
    stage ('maven version') {
      steps {               
          sh 'mvn -version'                
       }
     }

	stage('Build') {
	  steps {
         sh 'mvn -DskipTests clean install'
      }
    }
    
    stage('Test') {
	  steps {
         sh 'mvn test'
      }
    }
    
  }
}