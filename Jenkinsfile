pipeline {
  agent {
        docker {
          label 'master'
          image 'maven:3-jdk-8'
          args '-v /root/.m2:/root/.m2 -v /root/.ssh:/root/.ssh'
        }
      }
  stages {
    stage('Build') {
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }
    stage('Unit Tests') {
      post {
        always {
          junit 'target/surefire-reports/*.xml'
        }
      }
      steps {
        script {
          sh 'mvn --batch-mode resources:testResources compiler:testCompile surefire:test'
        }

      }
    }
    stage('Archiving') {
      steps {
        archiveArtifacts 'target/*.war, target/*.jar'
      }
    }
  }
}
