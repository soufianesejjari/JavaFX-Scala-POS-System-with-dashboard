-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : Dim 15 jan. 2023 à 15:27
-- Version du serveur :  5.7.31
-- Version de PHP : 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `scala`
--

-- --------------------------------------------------------

--
-- Structure de la table `administrateur`
--

DROP TABLE IF EXISTS `administrateur`;
CREATE TABLE IF NOT EXISTS `administrateur` (
  `id_admin` varchar(30) NOT NULL,
  `nom_admin` varchar(30) NOT NULL,
  `prenom_admin` varchar(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `adresse` varchar(30) NOT NULL,
  `telephone` int(11) NOT NULL,
  PRIMARY KEY (`id_admin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `administrateur`
--

INSERT INTO `administrateur` (`id_admin`, `nom_admin`, `prenom_admin`, `username`, `password`, `adresse`, `telephone`) VALUES
('1', 'sejjari', 'soufiane', 'soufiane', 'pass', '456 hjklmù', 612345678),
('2', 'sejjar', 'soufiane', 'soufiane', 'pas', '456 hjklmù', 612345678);

-- --------------------------------------------------------

--
-- Structure de la table `caissier`
--

DROP TABLE IF EXISTS `caissier`;
CREATE TABLE IF NOT EXISTS `caissier` (
  `id_caissier` int(30) NOT NULL AUTO_INCREMENT,
  `id_admin` varchar(30) NOT NULL,
  `nom_caissier` varchar(30) NOT NULL,
  `prenom_caissier` varchar(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `ville` varchar(30) NOT NULL,
  `telephone` int(11) NOT NULL,
  PRIMARY KEY (`id_caissier`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `caissier`
--

INSERT INTO `caissier` (`id_caissier`, `id_admin`, `nom_caissier`, `prenom_caissier`, `username`, `password`, `ville`, `telephone`) VALUES
(1, '1', 'SAID', 'munir', 'user', 'pass', 'ssssssssss', 612345678),
(2, '2', 'saft', 'amin', 'amine', 'amine', 'amine adres', 523456),
(3, '1', 'tawfiq', 'morad', 'morad', 'morad', 'fes', 6123456),
(4, '2', 'niedef', 'ghita', 'usrname', '12345678', 'casa', 3456789);

-- --------------------------------------------------------

--
-- Structure de la table `commandes`
--

DROP TABLE IF EXISTS `commandes`;
CREATE TABLE IF NOT EXISTS `commandes` (
  `id_commande` int(30) NOT NULL AUTO_INCREMENT,
  `id_caissier` varchar(30) NOT NULL,
  `date_commande` date NOT NULL,
  `totalePrix` int(11) NOT NULL,
  PRIMARY KEY (`id_commande`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `commandes`
--

INSERT INTO `commandes` (`id_commande`, `id_caissier`, `date_commande`, `totalePrix`) VALUES
(1, '1', '2022-12-22', 899),
(2, '2', '2022-12-31', 14),
(3, '1', '2022-12-31', 220),
(4, '2', '2022-12-31', 112),
(5, '1', '2022-12-31', 20),
(6, '2', '2022-12-31', 144),
(7, '1', '2022-12-31', 52),
(8, '1', '2022-12-31', 52),
(9, '1', '2022-12-31', 52),
(10, '1', '2022-12-31', 72),
(11, '1', '2022-12-31', 92),
(12, '1', '2023-01-03', 1272),
(13, '1', '2023-01-08', 190),
(14, '1', '2023-01-09', 584),
(15, '1', '2023-01-09', 12),
(16, '1', '2023-01-14', 17997),
(17, '1', '2023-01-14', 38173),
(18, '3', '2023-01-15', 5399);

-- --------------------------------------------------------

--
-- Structure de la table `commande_details`
--

DROP TABLE IF EXISTS `commande_details`;
CREATE TABLE IF NOT EXISTS `commande_details` (
  `id_commande_details` int(30) NOT NULL AUTO_INCREMENT,
  `id_commande` varchar(30) NOT NULL,
  `id_produit` varchar(30) NOT NULL,
  `quantite` int(11) NOT NULL,
  `total` int(11) NOT NULL,
  PRIMARY KEY (`id_commande_details`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `commande_details`
--

INSERT INTO `commande_details` (`id_commande_details`, `id_commande`, `id_produit`, `quantite`, `total`) VALUES
(1, '1', '1', 15, 508),
(2, '7', '1235', 1, 20),
(3, '7', '1234', 1, 12),
(4, '8', '1235', 1, 20),
(5, '8', '1234', 1, 12),
(6, '9', '1235', 1, 20),
(7, '9', '1234', 1, 12),
(8, '10', '1235', 1, 20),
(9, '11', '1234', 3, 36),
(10, '11', '1235', 1, 20),
(11, '12', '1234', 1, 12),
(12, '12', '1235', 12, 240),
(13, '13', '1235', 1, 20),
(14, '13', '1234', 15, 150),
(15, '14', '1235', 2, 40),
(16, '14', '1234', 1, 12),
(17, '14', '1234', 1, 12),
(18, '14', '1234', 12, 144),
(19, '14', '1234', 1, 12),
(20, '15', '1234', 1, 12),
(21, '16', '1232', 1, 4999),
(22, '16', '1231', 1, 7999),
(23, '17', '1232', 2, 9998),
(24, '17', '1234', 15, 180),
(25, '18', '3421', 1, 200),
(26, '18', '1232', 1, 4999);

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

DROP TABLE IF EXISTS `produit`;
CREATE TABLE IF NOT EXISTS `produit` (
  `id_produit` int(30) NOT NULL AUTO_INCREMENT,
  `nom_produit` varchar(30) NOT NULL,
  `categorie_produit` varchar(30) NOT NULL,
  `prix` int(11) NOT NULL,
  `code_QR` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_produit`)
) ENGINE=InnoDB AUTO_INCREMENT=3422 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id_produit`, `nom_produit`, `categorie_produit`, `prix`, `code_QR`) VALUES
(1231, 'iphone x', 'telephones', 7999, 1231),
(1232, 'samsung s1', 'telephones', 4999, 1231),
(1234, 'batata', 'legumes', 12, 123412),
(1235, 'tefaha', 'fruit', 20, 123520),
(3421, 'Smart watch', 'telephones', 200, NULL);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
