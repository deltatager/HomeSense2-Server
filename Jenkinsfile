pipeline {
  agent none
  stages {
    stage('Build') {
      agent {
        docker {
          label 'master'
          image 'maven:3-alpine'
          args '-v /root/.m2:/root/.m2'
        }
      }
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }
    stage('Unit Tests') {
      agent {
        docker {
          label 'master'
          image 'maven:3-alpine'
          args '-v /root/.m2:/root/.m2'
        }
      }
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
      agent {
        docker {
          image 'maven:3-alpine'
          label 'master'
          args '-v /root/.m2:/root/.m2'
        }
      }
      steps {
        archiveArtifacts 'target/*.war, target/*.jar'
      }
    }

    stage('Deploying') {
      agent {label 'master'}
      steps {
        sh 'whoami'
        sh 'scp'
        sh './deploy.sh'
      }
    }
  }
}
