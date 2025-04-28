# Comment utiliser le script run-microservices.sh

## 1. Créer le fichier

Ouvrez un terminal et créez le fichier avec la commande suivante :

```bash
nano run-microservices.sh
```

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