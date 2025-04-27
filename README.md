Voici la documentation formatée en Markdown pour expliquer comment utiliser le script `run-microservices.sh` :

```markdown
# Comment utiliser ce script

## 1. Créer le fichier

Ouvrez un terminal et créez le fichier avec la commande suivante :

```bash
nano run-microservices.sh
```

Copiez-collez le contenu dans l'éditeur et enregistrez le fichier.

## 2. Rendre le script exécutable

Une fois le fichier créé, rendez-le exécutable avec la commande :

```bash
chmod +x run-microservices.sh
```

## 3. Commandes disponibles

Voici les commandes que vous pouvez utiliser avec le script `run-microservices.sh` :

```bash
# Build toutes les images
./run-microservices.sh --build

# Démarrer tous les services
./run-microservices.sh --start

# Arrêter tous les services
./run-microservices.sh --stop

# Nettoyer complètement (containers, images, réseaux)
./run-microservices.sh --clean
```
```

### Explications

- **Étape 1** : Créer le fichier `run-microservices.sh` avec l'éditeur de texte `nano`.
- **Étape 2** : Utiliser `chmod` pour donner les permissions d'exécution au script.
- **Étape 3** : Expliquer les différentes options disponibles pour exécuter le script.

Si vous avez d'autres questions ou besoin d'informations supplémentaires, n'hésitez pas à demander !