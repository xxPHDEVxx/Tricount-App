# Projet Tricount

## Description

Le projet **Tricount** est une application de gestion des dépenses entre utilisateurs, conçue pour simplifier le suivi des dépenses partagées lors de projets communs, comme des voyages, des sorties entre amis, ou tout autre événement nécessitant la répartition des coûts. Développée en Java avec l'utilisation de la librairie graphique JavaFX, cette application offre une interface utilisateur intuitive et conviviale permettant de créer, gérer et partager des comptes de dépenses (appelés "Tricounts") entre plusieurs participants.

## Fonctionnalités

- **Création et gestion des Tricounts** : Créez des Tricounts pour différents événements, ajoutez des participants et enregistrez les dépenses.
- **Répartition automatique** : Calculez automatiquement la part de chacun pour simplifier les remboursements.
- **Affichage des comptes** : Visualisez les comptes en cours et les détails des transactions de manière claire et organisée.
- **Interface utilisateur moderne** : Utilise JavaFX pour une expérience utilisateur correcte.
- **Sauvegarde et récupération des données** : Conservez vos Tricounts et données d'utilisateurs en local pour une utilisation future.

## Prérequis

- **Java Development Kit (JDK)** version 17 ou supérieure
- **JavaFX** version 11 ou supérieure
- **Maven** pour la gestion des dépendances

## Installation

1. Clonez le dépôt GitHub du projet :

    ```bash
    git clone https://github.com/xxPHDEVxx/votre-projet-tricount.git
    cd votre-projet-tricount
    ```

2. Importez le projet dans votre IDE Java préféré (Eclipse, IntelliJ, NetBeans, etc.).

3. Assurez-vous que les dépendances JavaFX sont bien configurées.

4. Compilez et exécutez l'application à partir de votre IDE.

## Utilisation

1. **Connexion** : Pour commencer, connectez-vous en utilisant l'un des comptes préenregistrés.
    - Liste des utilisateurs et mots de passe :
        * `lala@gmail.com / Password1`

2. **Création d'un Tricount** : Après la connexion, créez un nouveau Tricount en saisissant les détails de l'événement et en ajoutant les participants.

3. **Ajout des dépenses** : Pour chaque dépense, ajoutez le montant, le payeur et les participants concernés. L'application calcule automatiquement la part de chacun.

4. **Visualisation des comptes** : Consultez à tout moment l'état des comptes, les montants dus et les remboursements effectués.

## Notes de version

### Version actuelle : 1.0.0

- **Ajout des fonctionnalités de base** : Création et gestion des Tricounts, répartition des dépenses, et interface utilisateur.
- **Gestion des comptes utilisateurs** : Authentification basique pour l'accès aux Tricounts personnels.

### Liste des utilisateurs et mots de passe

- `lala@gmail.com / Password1`

### Liste des bugs connus

- **Affichage de moins de 3 Tricounts** : Les cartes de Tricount sont trop larges et déforment l'affichage.

