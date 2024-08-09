pipeline {
    agent any
    stages {
        stage ('Compile Stage') {

            steps {
                withMaven(maven : 'M2_HOME') {
                    bat 'mvn clean compile'
                }
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test'
            }


     }
    }
 }