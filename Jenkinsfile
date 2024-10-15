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

        stage('Install Shared Library') {
            steps {
                sh "mvn install:install-file -Dfile=src/main/resources/sharedlibs/shared-lib-local-1.0.jar \
                -DgroupId=com.shared \
                -DartifactId=shared-lib-local \
                -Dversion=1.0 \
                -Dpackaging=jar"
            }
        }
        stage('Run Unit Tests'){
            steps{
                sh 'mvn clean test -Djasypt.encryptor.password=$JASYPT_PWD -DDB_HOST=postgres'
            }
        }

        stage('Build Java Project') {
             steps {
                  sh 'mvn clean install -Djasypt.encryptor.password=$JASYPT_PWD -DDB_HOST=postgres'
                  }
        }

        stage('Static Code Analysis') {
                    steps  {

                         sh '''
                         mvn clean verify sonar:sonar \
                              -Dsonar.projectKey=financial-grant-mgmt \
                              -Dsonar.projectName='financial-grant-mgmt' \
                              -Dsonar.host.url=http://host.docker.internal:9000 \
                              -Dsonar.token=$SONARQUBE_TOKEN \
                              -Djasypt.encryptor.password=$JASYPT_PWD \
                              -DDB_HOST=postgres
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