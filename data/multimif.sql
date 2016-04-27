-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Mer 02 Décembre 2015 à 03:05
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `multimif`
--

-- --------------------------------------------------------

--
-- Structure de la table `AssociationUserEvent`
--

CREATE TABLE IF NOT EXISTS `AssociationUserEvent` (
  `idUser` int(11) NOT NULL,
  `idEvent` int(11) NOT NULL,
  PRIMARY KEY (`idUser`,`idEvent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Structure de la table `Conference`
--

CREATE TABLE IF NOT EXISTS `Conference` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(120) NOT NULL,
  `directeur_id` int(11) NOT NULL,
  `theme` varchar(120) NOT NULL,
  `description` text DEFAULT NULL,
  `dateCreation` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idDirecteur` (`directeur_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=195 ;

--
-- Structure de la table `Event`
--

CREATE TABLE IF NOT EXISTS `Event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(120) NOT NULL,
  `idConf` int(11) NOT NULL,
  `uri` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=48 ;

--
-- Structure de la table `Publication`
--

CREATE TABLE IF NOT EXISTS `Publication` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idEvent` int(11) NOT NULL,
  `uri` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=48 ;

--
-- Structure de la table `AssociationPublicationEvent`
--

CREATE TABLE IF NOT EXISTS `AssociationUserEvent` (
  `idPublication` int(11) NOT NULL,
  `idEvent` int(11) NOT NULL,
  PRIMARY KEY (`idPublication`,`idEvent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Structure de la table `Utilisateur`
--

CREATE TABLE IF NOT EXISTS `Utilisateur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mail` varchar(120) NOT NULL,
  `pseudo` varchar(120) DEFAULT NULL,
  `motDePasse` varchar(120) DEFAULT NULL,
  `idFacebook` int(11) DEFAULT NULL,
  `idGravatar` int(11) DEFAULT NULL,
  `idTwitter` int(11) DEFAULT NULL,
  `nom` varchar(120) DEFAULT NULL,
  `prenom` varchar(120) DEFAULT NULL,
  `pays` varchar(120) DEFAULT NULL,
  `pageWeb` varchar(120) DEFAULT NULL,
  `affiliation` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mail` (`mail`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=165 ;

--
-- Contraintes pour la table `Conference`
--
ALTER TABLE `Conference`
  ADD CONSTRAINT `conference_ibfk_1` FOREIGN KEY (`directeur_id`) REFERENCES `Utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
