/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-12.0.2-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: bookHub
-- ------------------------------------------------------
-- Server version	12.0.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Table structure for table `lista`
--

DROP TABLE IF EXISTS `lista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `lista` (
  `id_lista` int(11) NOT NULL AUTO_INCREMENT,
  `id_perfil` int(11) NOT NULL,
  `nome_lista` varchar(100) NOT NULL,
  `qt_livro` int(11) DEFAULT 0,
  `data_criacao` date DEFAULT NULL,
  PRIMARY KEY (`id_lista`),
  KEY `id_perfil` (`id_perfil`),
  CONSTRAINT `lista_ibfk_1` FOREIGN KEY (`id_perfil`) REFERENCES `perfil` (`id_perfil`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lista`
--

LOCK TABLES `lista` WRITE;
/*!40000 ALTER TABLE `lista` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `lista` VALUES
(1,1,'Favoritos',0,'2025-11-26'),
(2,1,'Quero Ler',0,'2025-11-26'),
(3,1,'Lendo',0,'2025-11-26'),
(4,1,'Lidos',0,'2025-11-26');
/*!40000 ALTER TABLE `lista` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `lista_livro`
--

DROP TABLE IF EXISTS `lista_livro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `lista_livro` (
  `id_lista` int(11) NOT NULL,
  `id_livro` int(11) NOT NULL,
  `data_adicionado` date NOT NULL,
  `status` enum('FAVORITO','LENDO','LIDO','QUERO_LER','INDEFINIDO') NOT NULL DEFAULT 'INDEFINIDO',
  `avaliacao` tinyint(3) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_lista`,`id_livro`),
  KEY `id_livro` (`id_livro`),
  CONSTRAINT `lista_livro_ibfk_1` FOREIGN KEY (`id_lista`) REFERENCES `lista` (`id_lista`),
  CONSTRAINT `lista_livro_ibfk_2` FOREIGN KEY (`id_livro`) REFERENCES `livro` (`id_livro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lista_livro`
--

LOCK TABLES `lista_livro` WRITE;
/*!40000 ALTER TABLE `lista_livro` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `lista_livro` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `livro`
--

DROP TABLE IF EXISTS `livro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `livro` (
  `id_livro` int(11) NOT NULL AUTO_INCREMENT,
  `capa` varchar(255) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `autor` varchar(255) NOT NULL,
  `ano_publicacao` int(11) NOT NULL,
  `isbn` varchar(80) NOT NULL,
  `genero` varchar(100) NOT NULL,
  `qt_pagina` int(11) NOT NULL,
  `idioma` varchar(20) NOT NULL,
  `descricao` varchar(2000) NOT NULL,
  PRIMARY KEY (`id_livro`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `livro`
--

LOCK TABLES `livro` WRITE;
/*!40000 ALTER TABLE `livro` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `livro` VALUES
(44,'capas/memoriasPostumasBrasCubas.jpg','Memórias Póstumas de Brás Cubas','Machado de Assis',1997,'85-250-1762-0','Clássico',208,'Português','AO VERME QUE PRIMEIRO ROEU AS FRIAS CARNES DO MEU CADÁVER DEDICO COMO SAUDOSA LEMBRANÇA ESTAS MEMÓRIAS PÓSTUMAS'),
(45,'capas/DomCasmurro.jpg','Dom Casmurro','Machado de Assis',1899,'85-250-1761-2','Clássico',218,'Português','O livro conta a história de Bentinho e Capitu, desde o namoro infantil até o casamento atormentado pelo ciúme e pela dúvida que virou polêmica literária: Capitu traiu o marido com o melhor amigo dele, Escobar? Os fatos são narrados por Bentinho, que relembra, já velho, episódios de sua vida. Dom Casmurro faz parte da “fase de maturidade” de Machado de Assis.'),
(46,'capas/MaoLuva.jpg','A Mão e a Luva','Machado de Assis',1874,'85-250-1763-9','Clássico',108,'Português','O romance conta a história de Guiomar, jovem de origem humilde que, de modo frio e interesseiro, planeja ascender socialmente. Para isso, precisa controlar os impulsos do coração de forma puramente racional. Aparenta fragilidade e pureza, quando, na verdade, é só determinada a seguir os próprios interesses.'),
(47,'capas/EdgarAllanPoeMedoClassico.jpg','Edgar Allan Poe: Medo Clássico','Edgar Allan Poe',2016,'978-85-945-4024-9','Horror',384,'Português','Seguindo o padrão quase psicopata de qualidade que os leitores já esperam da DarkSide® Books, o livro é uma homenagem a Poe em todos os detalhes: da capa dura à nova tradução feita por Marcia Heloisa, pesquisadora e tradutora do gênero, além das belíssimas ilustrações em xilogravura feitas pelo artista gráfico Ramon Rodrigues. E o mais importante: o conteúdo selecionado que recheia as 384 páginas deste primeiro volume de Edgar Allan Poe: Medo Clássico. E que conteúdo!\n\nPela primeira vez numa edição nacional, os contos estão divididos em blocos temáticos que ajudam a visualizar a enorme abrangência da obra. A morte, narradores homicidas, mulheres imortais, aventuras, as histórias do detetive Auguste Dupin, personagem que serviu de inspiração para Sherlock Holmes.\n'),
(48,'capas/LabirintoFauno.jpg','O Labirinto do Fauno','Guillermo del Toro, Cornelia Funker',2019,'978-85-510-0519-4','Fantasia',320,'Português','No ano de 1944, Ofélia e a mãe cruzam uma estrada de terra que corta uma floresta longínqua ao norte da Espanha, um lugar que guarda histórias já esquecidas pelos homens. O novo lar é um moinho de vento tomado pela escuridão e pela crueldade do capitão Vidal e seus soldados, dispostos a tudo para exterminar os rebeldes que se escondem na mata.\n\nMas o que eles não sabem é que a floresta que tanto odeiam também abriga criaturas mágicas e poderosas, habitantes de um reino subterrâneo repleto de encantos e horrores, súditos em busca de sua princesa há muito perdida. Uma princesa que, segundo os sussurros das árvores, finalmente retornou ao lar.\n'),
(49,'capas/ItCoisa.jpg','It: A coisa','Stephen King',2018,'9788560280940','Horror',1104,'Português','Durante as férias de 1958, em uma pacata cidadezinha chamada Derry, um grupo de sete amigos começa a ver coisas estranhas. Um conta que viu um palhaço, outro que viu uma múmia. Finalmente, acabam descobrindo que estavam todos vendo a mesma coisa: um ser sobrenatural e maligno que pode assumir várias formas. É assim que Bill, Beverly, Eddie, Ben, Richie, Mike e Stan enfrentam a Coisa pela primeira vez.\n\nQuase trinta anos depois, o grupo volta a se encontrar. Mike, o único que permaneceu em Derry, dá o sinal ― uma nova onda de terror tomou a pequena cidade. É preciso unir forças novamente. Só eles têm a chave do enigma. Só eles sabem o que se esconde nas entranhas de Derry. Só eles podem vencer a Coisa.'),
(50,'capas/Sapiens.jpg','Sapiens: uma breve história da humanidade','Yuval Noah Harari',2020,'9788535933925','História',472,'Português','O planeta Terra tem cerca de 4,5 bilhões de anos. Numa fração ínfima desse tempo, uma espécie entre incontáveis outras o dominou: nós, humanos. Somos os animais mais evoluídos e mais destrutivos que jamais viveram.\nSapiens é a obra-prima de Yuval Noah Harari e o consagrou como um dos pensadores mais brilhantes da atualidade. Num feito surpreendente, que já fez deste livro um clássico contemporâneo, o historiador israelense aplica uma fascinante narrativa histórica a todas as instâncias do percurso humano sobre a Terra. Da Idade da Pedra ao Vale do Silício, temos aqui uma visão ampla e crítica da jornada em que deixamos de ser meros símios para nos tornarmos os governantes do mundo.'),
(51,'capas/MarcaDeAtena.jpg','A Marca de Atena','Rick Riordan',2013,'9788580573107','Fantasia',480,'Português','A bordo do Argo II com os amigos Jason, Piper e Leo, ela não pode culpar os semideuses romanos por pensarem que o navio é uma arma de guerra grega: afinal, com um dragão de bronze fumegante como figura de proa, a fantástica criação de Leo não parece mesmo nada amigável. Annabeth só pode torcer para que os romanos vejam seu pretor Jason na embarcação e compreendam que os visitantes do Acampamento Meio-Sangue estão ali em missão de paz.\n\nOs problemas de Annabeth não param por aí ― ela carrega no bolso um presente da mãe, que veio acompanhado de uma ordem intimidadora: Siga a Marca de Atena. Vingue-me. A guerreira já carrega nas costas o peso da profecia que mandará sete semideuses em busca das Portas da Morte. O que mais Atena poderia querer dela? O maior medo de Annabeth, no entanto, é que Percy tenha mudado. E se ele já estiver habituado demais aos costumes romanos? Será que ainda precisará dos velhos amigos? Como filha da deusa da guerra e da sabedoria, Annabeth sabe que nasceu para liderar ― no entanto, também sabe que nunca mais vai querer viver sem o Cabeça de Alga.'),
(52,'capas/FilhoDeNetuno.jpg','O Filho de Netuno','Rick Riordan',2012,'9788580571806','Fantasia',432,'Português','Filho de Poseidon, o deus do mar, um belo dia Percy desperta sem memória e acaba em um acampamento de heróis que não reconhece. Agarrado à lembrança de uma garota, só tem uma certeza: os dias de jornadas e batalhas não terminaram. Percy e seus novos colegas semideuses vão enfrentar os misteriosos desígnios da Profecia dos Sete.\n\nSe falharem, as consequências, é claro, serão desastrosas. Com início no “outro” acampamento meio-sangue e se estendendo para além das terras dos deuses, esta sequência da série Os heróis do Olimpo apresenta novos semideuses e criaturas incríveis, além de trazer de volta alguns monstros bastante conhecidos.'),
(53,'capas/HeroiPerdido.jpg','O Herói Perdido','Rick Riordan',2011,'9788580570083','Fantasia',440,'Português','Filho de Poseidon, o deus do mar, um belo dia Percy desperta sem memória e acaba em um acampamento de heróis que não reconhece. Agarrado à lembrança de uma garota, só tem uma certeza: os dias de jornadas e batalhas não terminaram. Percy e seus novos colegas semideuses vão enfrentar os misteriosos desígnios da Profecia dos Sete.\n\nSe falharem, as consequências, é claro, serão desastrosas. Com início no “outro” acampamento meio-sangue e se estendendo para além das terras dos deuses, esta sequência da série Os heróis do Olimpo apresenta novos semideuses e criaturas incríveis, além de trazer de volta alguns monstros bastante conhecidos.'),
(54,'capas/Frankstein.jpg','Frankenstein, ou o Prometeu Moderno','Mary Wollstonecraft Shelley',2017,'9788594540188','Clássico',304,'Português','Victor é um cientista que dedica a juventude e a saúde para descobrir como reanimar tecidos mortos e gerar vida artificialmente. O resultado de sua experiência, um monstro que o próprio Frankenstein considera uma aberração, ganha consciência, vontade, desejo, medo. Criador e criatura se enfrentam: são opostos e, de certa forma, iguais. Humanos! Eis a força descomunal de um grande texto.\n\nQuando foi a última vez que você teve a chance de entrar em contato com a narrativa original desse que é um dos romances mais influentes dos últimos dois séculos? Que tal agora, na tradução de Márcia Xavier de Brito? Além disso, esta edição conta com quatro contos sobre a Imortalidade, em que Shelley continua a explorar os perigos e percalços daqueles que se arriscam à tentação de criar vida: “Valério: O Romano Reanimado”; “Roger Dodsworth: O Inglês Reanimado”; “Transformação”; e “O Imortal Mortal”, histórias pesquisadas e traduzidas por Carlos Primati, estudioso do gênero.\n\nFrankenstein, ou o Prometeu Moderno é um dos primeiros lançamentos da coleção Medo Clássico — ao lado do volume de contos do mestre Edgar Allan Poe — no início de 2017. A qualidade do livro é impecável, para cientista maluco nenhum colocar defeito. Capa dura, novas traduções, ilustrações feitas por Pedro Franz, artista visual e autor de quadrinhos reconhecido internacionalmente. O livro é impresso em duas cores: preto e sangue.\n\nReencontre Frankenstein de um jeito que só a primeira editora brasileira inteiramente dedicada ao terror e à fantasia poderia lançar. It’s alive!'),
(55,'capas/MessiasDuna.jpg','Messias de Duna','Frank Herbert',2017,'9788576573821','Ficção Científica',272,'Português','Doze anos se passaram desde que Paul Atreides ascendeu ao trono e acumulou os títulos de imperador e messias. Líder do maior império que a humanidade já viu, Paul está terrivelmente consciente do peso de suas decisões. Arrakis tornou-se o centro do Imperium, de onde os fremen se propagaram a fim de levar sua filosofia e forma de governar aos planetas por eles conquistados. Os inevitáveis conflitos gerados por essa expansão fazem importantes facções contrárias ao imperador reunirem forças para detê-lo. Uma grande disputa está prestes a ter início nos bastidores do poder, e apenas Muad’Dib pode decidir o destino de todos.\n\nMessias de Duna é o segundo volume da série criada por Frank Herbert. Ele revela um lado mais humano de seus personagens, além de aprofundar e estender o universo de Duna, aliando discussões políticas, filosóficas e religiosas à épica história de poder, vingança e redenção.'),
(56,'capas/FilhosDuna.jpg','Filhos de Duna','Frank Herbert',2017,'9788576573142','Ficção Científica',528,'Português','O Imperium vive um interregno após Paul Atreides abdicar de seu título de imperador e entregar-se ao deserto. Sua irmã, Alia, ascende ao poder como regente enquanto os filhos de Muad\'Dib não são capazes de assumir o Trono do Leão. Herdeiros não só do poder político e econômico de seu pai, os gêmeos também carregam em suas veias toda a carga genética manipulada por séculos pela Irmandade Bene Gesserit. Mas a hegemonia dos Atreides está ameaçada.\n\nDe seu exílio em Salusa Secundus, os membros da despojada Casa Corrino tramam uma complexa teia para retomar as rédeas do Imperium e se vingar de todos os envolvidos em sua queda. Filhos de Duna fecha com brilhantismo o arco de história iniciado com Paul Atreides no épico Duna e em sua sequência, Messias de Duna, retomando os temas políticos e existenciais com a mesma maestria dos livros que o precederam.'),
(57,'capas/Duna.jpg','Duna','Frank Herbert',2017,'9788576573135','Ficção Científica',677,'Português','\"Não conheço nada que se compare a este livro, a não ser O Senhor dos Anéis.\" - Arthur C. Clarke\n\nA vida do jovem Paul Atreides está prestes a mudar radicalmente. Após a visita de uma mulher misteriosa, ele é obrigado a deixar seu planeta natal para sobreviver ao ambiente árido e severo de Arrakis, o Planeta Deserto.\n\nEnvolvido numa intrincada teia política e religiosa, Paul divide-se entre as obrigações de herdeiro e seu treinamento nas doutrinas secretas de uma antiga irmandade, que vê nele a esperança de realização de um plano urdido há séculos.\n\nEcos de profecias ancestrais também o cercam entre os nativos de Arrakis. Seria ele o eleito que tornaria viáveis seus sonhos e planos ocultos?\n\nAo lado das trilogias Fundação, de Isaac Asimov, e O Senhor dos Anéis, de J. R. R. Tolkien, Duna é considerada uma das maiores obras de fantasia e ficção científica de todos os tempos. Um premiado best-seller já levado às telas de cinema pelas mãos do consagrado diretor David Lynch.'),
(58,'capas/EllaMinnowPea.jpg','Ella Minnow Pea: A Novel in Letters','Mark Dunn',2002,'9780385722438 ','Ficção',208,'Inglês','Ella Minnow Pea is a girl living happily on the fictional island of Nollop off the coast of South Carolina. Nollop was named after Nevin Nollop, author of the immortal pangram,* \"The quick brown fox jumps over the lazy dog.\" Now Ella finds herself acting to save her friends, family, and fellow citizens from the encroaching totalitarianism of the island\'s Council, which has banned the use of certain letters of the alphabet as they fall from a memorial statue of Nevin Nollop. As the letters progressively drop from the statue they also disappear from the novel. The result is both a hilarious and moving story of one girl\'s fight for freedom of expression, as well as a linguistic tour de force sure to delight word lovers everywhere.\n\n*pangram: a sentence or phrase that includes all the letters of the alphabet'),
(59,'capas/OIluminado.jpg','O Iluminado','Stephen King',2017,'9788556510464','Horror',520,'Português','O lugar perfeito para recomeçar, é o que pensa Jack Torrance ao ser contratado como zelador para o inverno. Hora de deixar para trás o alcoolismo, os acessos de fúria e os repetidos fracassos. Isolado pela neve com a esposa e o filho, tudo o que Jack deseja é um pouco de paz para se dedicar à escrita.\n\nMas, conforme o inverno se aprofunda, o local paradisíaco começa a parecer cada vez mais remoto... e sinistro. Forças malignas habitam o Overlook, e tentam se apoderar de Danny Torrance, um garotinho com grandes poderes sobrenaturais.\n\nPossuir o menino, no entanto, se mostra mais difícil do que esperado. Então os espíritos resolvem se aproveitar das fraquezas do pai...\n\nUm dos livros mais assustadores de todos os tempos, O iluminado é uma das obras mais consagradas do terror.');
/*!40000 ALTER TABLE `livro` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `perfil` (
  `id_perfil` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) NOT NULL,
  `fotoPerfil` varchar(255) DEFAULT NULL,
  `imagem_fundo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_perfil`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `perfil_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `perfil` VALUES
(1,1,'/home/MasfStrange/IdeaProjects/BookHub/src/main/resources/com/example/bookhub/img/fotoPerfilTeste.JPG','/home/MasfStrange/IdeaProjects/BookHub/src/main/resources/com/example/bookhub/img/login_register_background.jpg');
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nome_usuario` varchar(30) NOT NULL,
  `senha` varchar(30) NOT NULL,
  `email` varchar(100) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `sobrenome` varchar(30) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `nome_usuario` (`nome_usuario`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `usuario` VALUES
(1,'teste123','teste123','teste123@gmail.com','teste123','teste123');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping routines for database 'bookHub'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2025-11-26 20:47:04
