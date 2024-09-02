pipeline {
    agent any
     tools {
        maven 'local_maven'
     }

     environment {
             SONARQUBE_TOKEN = credentials('sonartoken_finmgmt')
             JASYPT_PWD =  credentials('jasypt_secret')
     }

    stages {
        stage('Run Unit Tests'){
            steps{
                sh 'mvn clean test'
            }
        }

        stage('Build Java Project') {
             steps {
                  sh 'mvn clean install -Djasypt.encryptor.password=$JASYPT_PWD'
                  }
        }

        stage('Static Code Analysis') {
                    steps  {

                         sh '''
                         mvn clean verify sonar:sonar \
                              -Dsonar.projectKey=financial-grant-mgmt \
                              -Dsonar.projectName='financial-grant-mgmt' \
                              -Dsonar.host.url=http://host.docker.internal:9000 \
                              -Dsonar.token=$SONARQUBE_TOKEN
                              '''
                   }
         }

       stage('Create Docker image') {
            steps {
                script {
                    sh 'docker build -t basith321/financial-grant-mgmt_linux:v0.1 .'
                }
            }
       }

       stage('Docker Hub: Image Push') {
                   steps {
                       script {
                           sh 'docker push basith321/financial-grant-mgmt_linux:v0.1'
                       }
                   }
        }

    }
}