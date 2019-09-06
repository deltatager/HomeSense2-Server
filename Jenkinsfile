pipeline {
  agent {
    docker {
      image 'maven:3-alpine'
      args '-v /root/.m2:/root/.m2'
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
    stage('Deploying') {
      steps {
        sh 'apk add openssh-client'
        sh '/usr/bin/scp target/*.war pi@pi1.deltanet.int:/opt/tomee8/webapps'
      }
    }
  }
}
