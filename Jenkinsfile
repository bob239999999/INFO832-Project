pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }

    environment {
        SONAR_HOST_URL = 'http://gpu-epu.univ-savoie.fr:9000'
        SONAR_PROJECT_KEY = 'BBCCKS'
        SONAR_LOGIN = 'groupeBBCCKS'
        SONAR_PASSWORD = 'BBCCKS'
    }

    stages {
        stage('SCM') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    def maven = tool name: 'Maven', type: 'maven'
                    sh "${maven}/bin/mvn -B -DskipTests clean install"
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    def maven = tool name: 'Maven', type: 'maven'
                    sh "${maven}/bin/mvn test"
                }
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Javadoc') {
            steps {
                script {
                    def maven = tool name: 'Maven', type: 'maven'
                    sh "${maven}/bin/mvn javadoc:javadoc -DreportDir=target/site/apidocs"
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    def maven = tool name: 'Maven', type: 'maven'
                    withSonarQubeEnv('SonarQube') {
                        sh """
                        ${maven}/bin/mvn sonar:sonar \
                        -Dsonar.host.url=${SONAR_HOST_URL} \
                        -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                        -Dsonar.login=${SONAR_LOGIN} \
                        -Dsonar.password=${SONAR_PASSWORD} \
                        -Dsonar.java.binaries=target/classes
                        """
                    }
                }
            }
        }
    }
}
