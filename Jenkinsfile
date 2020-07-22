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
          sh 'mvn -B -DskipTests clean package'
        }
    }
  }
}