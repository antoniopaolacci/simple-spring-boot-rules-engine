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
          sh mvn -DskipTests clean install
        }
    }
  }
}