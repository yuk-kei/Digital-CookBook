/*
 Navicat Premium Data Transfer

 Source Server         : Navicat
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : cookbook

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 04/07/2021 07:31:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ingredient
-- ----------------------------
DROP TABLE IF EXISTS `ingredient`;
CREATE TABLE `ingredient`  (
  `recipe_id` int UNSIGNED NOT NULL,
  `name` varchar(80) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `quantity` decimal(10, 0) NULL DEFAULT NULL,
  `unit` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `description` varchar(80) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`recipe_id`, `name`) USING BTREE,
  INDEX `fk_Recipes_idx`(`recipe_id`) USING BTREE,
  CONSTRAINT `fk_ingredient_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`recipe_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ingredient
-- ----------------------------
INSERT INTO `ingredient` VALUES (34, 'breads', 2, 'pieces', '');
INSERT INTO `ingredient` VALUES (34, 'butter', 100, 'g', '');
INSERT INTO `ingredient` VALUES (34, 'eggs', 200, 'g', '');
INSERT INTO `ingredient` VALUES (34, 'salt', 3, 'g', '');
INSERT INTO `ingredient` VALUES (34, 'truffle', 1, 'piece', '');
INSERT INTO `ingredient` VALUES (35, 'apples', 200, 'g', '');
INSERT INTO `ingredient` VALUES (35, 'butter', 100, 'g', '');
INSERT INTO `ingredient` VALUES (35, 'ice-cream', 10, 'g', '');
INSERT INTO `ingredient` VALUES (35, 'puff pastry', 2, 'pieces', '');
INSERT INTO `ingredient` VALUES (35, 'sugar', 10, 'g', '');
INSERT INTO `ingredient` VALUES (37, 'Rosemary', 20, 'g', '');
INSERT INTO `ingredient` VALUES (37, 'black pepper salt', 5, 'g', '');
INSERT INTO `ingredient` VALUES (37, 'olive oil', 5, 'g', '');
INSERT INTO `ingredient` VALUES (37, 'rump of lamb', 500, 'g', '');
INSERT INTO `ingredient` VALUES (38, 'Rendang', 5, 'g', '');
INSERT INTO `ingredient` VALUES (38, 'Samba', 5, 'g', '');
INSERT INTO `ingredient` VALUES (38, 'chilies', 5, 'pieces', '');
INSERT INTO `ingredient` VALUES (38, 'chive blossoms', 10, 'pieces', '');
INSERT INTO `ingredient` VALUES (38, 'eggs', 150, 'g', '');
INSERT INTO `ingredient` VALUES (38, 'galangal', 1, 'piece', '');
INSERT INTO `ingredient` VALUES (38, 'leftover rice', 300, 'g', '');
INSERT INTO `ingredient` VALUES (38, 'oil', 5, 'g', '');
INSERT INTO `ingredient` VALUES (39, 'butter', 100, 'g', '');
INSERT INTO `ingredient` VALUES (39, 'chopped parley', 50, 'g', '');
INSERT INTO `ingredient` VALUES (39, 'crushed garlic', 20, 'g', '');
INSERT INTO `ingredient` VALUES (39, 'olive oil', 5, 'g', '');
INSERT INTO `ingredient` VALUES (39, 'shrimps', 300, 'g', '');
INSERT INTO `ingredient` VALUES (39, 'touch of taste', 5, 'g', '');
INSERT INTO `ingredient` VALUES (40, 'broad-bean sauce', 20, 'g', '');
INSERT INTO `ingredient` VALUES (40, 'cooking wine', 5, 'g', '');
INSERT INTO `ingredient` VALUES (40, 'egg white', 50, 'g', '');
INSERT INTO `ingredient` VALUES (40, 'garlic', 20, 'g', '');
INSERT INTO `ingredient` VALUES (40, 'grass carp slices', 500, 'g', '');
INSERT INTO `ingredient` VALUES (40, 'needle mushroom', 100, 'g', '');
INSERT INTO `ingredient` VALUES (40, 'pepper', 5, 'g', '');
INSERT INTO `ingredient` VALUES (40, 'pickled cabbage', 150, 'g', '');
INSERT INTO `ingredient` VALUES (40, 'salt', 5, 'g', '');
INSERT INTO `ingredient` VALUES (40, 'starches', 5, 'g', '');
INSERT INTO `ingredient` VALUES (41, 'eggs', 100, 'g', '');
INSERT INTO `ingredient` VALUES (41, 'green onion', 20, 'g', '');
INSERT INTO `ingredient` VALUES (41, 'oil', 10, 'g', '');
INSERT INTO `ingredient` VALUES (41, 'salt', 10, 'g', '');
INSERT INTO `ingredient` VALUES (41, 'sugar', 10, 'g', '');
INSERT INTO `ingredient` VALUES (41, 'tomatoes', 200, 'g', '');
INSERT INTO `ingredient` VALUES (43, 'glutinous rice flour', 50, 'g', '');
INSERT INTO `ingredient` VALUES (43, 'pumpkin', 250, 'g', '');
INSERT INTO `ingredient` VALUES (43, 'rock candy', 30, 'g', '');
INSERT INTO `ingredient` VALUES (44, 'beef', 150, 'g', '');
INSERT INTO `ingredient` VALUES (44, 'bitter gourd', 200, 'g', '');
INSERT INTO `ingredient` VALUES (44, 'cornstarch', 20, 'g', '');
INSERT INTO `ingredient` VALUES (44, 'oil', 10, 'g', '');
INSERT INTO `ingredient` VALUES (44, 'salt', 5, 'g', '');
INSERT INTO `ingredient` VALUES (44, 'soy sauce', 5, 'g', '');
INSERT INTO `ingredient` VALUES (45, 'broccoli', 200, 'g', '');
INSERT INTO `ingredient` VALUES (45, 'cook wine', 5, 'g', '');
INSERT INTO `ingredient` VALUES (45, 'fresh ginger', 1, 'piece', '');
INSERT INTO `ingredient` VALUES (45, 'garlic', 1, 'piece', '');
INSERT INTO `ingredient` VALUES (45, 'salt', 5, 'g', '');
INSERT INTO `ingredient` VALUES (45, 'shelled shrimps', 100, 'g', '');
INSERT INTO `ingredient` VALUES (45, 'starch', 5, 'g', '');
INSERT INTO `ingredient` VALUES (46, 'broad-bean sauce', 10, 'g', '');
INSERT INTO `ingredient` VALUES (46, 'garlic', 1, 'piece', '');
INSERT INTO `ingredient` VALUES (46, 'green onion', 10, 'g', '');
INSERT INTO `ingredient` VALUES (46, 'oil', 10, 'g', '');
INSERT INTO `ingredient` VALUES (46, 'onion', 50, 'g', '');
INSERT INTO `ingredient` VALUES (46, 'peppercorn', 10, 'g', '');
INSERT INTO `ingredient` VALUES (46, 'soy sauce', 5, 'g', '');
INSERT INTO `ingredient` VALUES (46, 'squid', 250, 'g', '');
INSERT INTO `ingredient` VALUES (47, 'broad-bean sauce', 30, 'g', '');
INSERT INTO `ingredient` VALUES (47, 'chopped beef', 50, 'g', '');
INSERT INTO `ingredient` VALUES (47, 'cooking wine', 10, 'g', '');
INSERT INTO `ingredient` VALUES (47, 'garlic', 20, 'g', '');
INSERT INTO `ingredient` VALUES (47, 'meat soup', 300, 'ml', '');
INSERT INTO `ingredient` VALUES (47, 'peanut oil', 30, 'g', '');
INSERT INTO `ingredient` VALUES (47, 'salt', 2, 'g', '');
INSERT INTO `ingredient` VALUES (47, 'soy sauce', 15, 'g', '');
INSERT INTO `ingredient` VALUES (47, 'starch', 20, 'g', '');
INSERT INTO `ingredient` VALUES (47, 'tofu', 400, 'g', '');
INSERT INTO `ingredient` VALUES (48, 'cooking wine', 10, 'g', '');
INSERT INTO `ingredient` VALUES (48, 'duck', 3500, 'g', '');
INSERT INTO `ingredient` VALUES (48, 'fresh ginger', 1, 'piece', '');
INSERT INTO `ingredient` VALUES (48, 'pepper', 10, 'g', '');
INSERT INTO `ingredient` VALUES (48, 'peppercorn', 15, 'g', '');
INSERT INTO `ingredient` VALUES (48, 'salt', 35, 'g', '');
INSERT INTO `ingredient` VALUES (48, 'star anise', 3, 'pieces', '');
INSERT INTO `ingredient` VALUES (49, 'bamboo shots', 2, 'pieces', '');
INSERT INTO `ingredient` VALUES (49, 'cooking wine', 10, 'g', '');
INSERT INTO `ingredient` VALUES (49, 'salt', 1, 'g', '');
INSERT INTO `ingredient` VALUES (49, 'salty pork', 150, 'g', '');
INSERT INTO `ingredient` VALUES (49, 'soy sauce', 5, 'g', '');
INSERT INTO `ingredient` VALUES (51, 'Mozzarella', 250, 'g', '');
INSERT INTO `ingredient` VALUES (51, 'basil', 50, 'g', '');
INSERT INTO `ingredient` VALUES (51, 'cherry tomatoes', 300, 'g', '');
INSERT INTO `ingredient` VALUES (51, 'olive oil', 10, 'g', '');
INSERT INTO `ingredient` VALUES (51, 'spaghetti', 400, 'g', '');
INSERT INTO `ingredient` VALUES (51, 'tomato sauce', 15, 'g', '');
INSERT INTO `ingredient` VALUES (51, 'vegetable stockpot', 1, 'piece', '');
INSERT INTO `ingredient` VALUES (60, 'black pepper salt', 5, 'g', '');
INSERT INTO `ingredient` VALUES (60, 'chicken breast', 500, 'g', '');
INSERT INTO `ingredient` VALUES (60, 'chicken stock', 300, 'ml', '');
INSERT INTO `ingredient` VALUES (60, 'garlic', 1, 'piece', '');
INSERT INTO `ingredient` VALUES (60, 'mushrooms', 100, 'g', '');
INSERT INTO `ingredient` VALUES (60, 'olive oil', 10, 'g', '');
INSERT INTO `ingredient` VALUES (60, 'onion', 1, 'piece', '');
INSERT INTO `ingredient` VALUES (60, 'pepper', 1, 'piece', '');
INSERT INTO `ingredient` VALUES (60, 'smoked paprika', 5, 'g', '');
INSERT INTO `ingredient` VALUES (60, 'white wine', 200, 'ml', '');
INSERT INTO `ingredient` VALUES (62, 'Garlic', 1, ' ', 'important flavor');
INSERT INTO `ingredient` VALUES (62, 'beef short ribs', 1, 'kg', '');
INSERT INTO `ingredient` VALUES (62, 'beef stock', 300, 'ml', 'main ingrediant');
INSERT INTO `ingredient` VALUES (62, 'black pepper salt', 5, 'g', '');
INSERT INTO `ingredient` VALUES (62, 'olive oil', 15, 'g', '');
INSERT INTO `ingredient` VALUES (62, 'red wine', 300, 'ml', 'cheap is enough');
INSERT INTO `ingredient` VALUES (63, 'cooking wine', 5, 'g', '');
INSERT INTO `ingredient` VALUES (63, 'fermented bean curd', 5, 'g', '');
INSERT INTO `ingredient` VALUES (63, 'honey', 30, 'g', '');
INSERT INTO `ingredient` VALUES (63, 'minced garlic', 5, 'g', '');
INSERT INTO `ingredient` VALUES (63, 'oyster sauce', 5, 'g', '');
INSERT INTO `ingredient` VALUES (63, 'soy sauce', 10, 'g', '');
INSERT INTO `ingredient` VALUES (63, 'streaky pork', 250, 'g', '');
INSERT INTO `ingredient` VALUES (63, 'sugar', 45, 'g', '');

-- ----------------------------
-- Table structure for preparationstep
-- ----------------------------
DROP TABLE IF EXISTS `preparationstep`;
CREATE TABLE `preparationstep`  (
  `recipe_id` int UNSIGNED NOT NULL,
  `step` int UNSIGNED NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`recipe_id`, `step`) USING BTREE,
  INDEX `fk_Recipes_idx`(`recipe_id`) USING BTREE,
  CONSTRAINT `fk_preparation_step_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`recipe_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of preparationstep
-- ----------------------------
INSERT INTO `preparationstep` VALUES (34, 0, 'Add olive oil and salt into the pan.');
INSERT INTO `preparationstep` VALUES (34, 1, 'Use the bread to mop the hot oil on the pan.');
INSERT INTO `preparationstep` VALUES (34, 2, 'Whisk your eggs together.');
INSERT INTO `preparationstep` VALUES (34, 3, 'Slice the truffle very thin and add them on your bread.');
INSERT INTO `preparationstep` VALUES (34, 4, 'Add butter into the egg, then pour them on the hot pan with oil.');
INSERT INTO `preparationstep` VALUES (34, 5, 'Add a little salt. Then slice some truffle.');
INSERT INTO `preparationstep` VALUES (34, 6, 'Put the egg into your breads.');
INSERT INTO `preparationstep` VALUES (35, 0, 'Cut your puff pastry round.');
INSERT INTO `preparationstep` VALUES (35, 1, 'Peel and slice the apples.');
INSERT INTO `preparationstep` VALUES (35, 2, 'Add some butter and sugar on the apple slices.');
INSERT INTO `preparationstep` VALUES (35, 3, 'Put into the oven. 190°C for 20 mins.');
INSERT INTO `preparationstep` VALUES (37, 0, 'Score the lamb.');
INSERT INTO `preparationstep` VALUES (37, 1, 'Slice the garlic and stuff it into the lamb. Then also stuff Rosemary into the lamb.');
INSERT INTO `preparationstep` VALUES (37, 2, 'Add the black pepper salt on the lamb.');
INSERT INTO `preparationstep` VALUES (37, 3, 'Pour oil on the hot pan. Then put the lamb in the pan.');
INSERT INTO `preparationstep` VALUES (37, 4, 'Put lamb into the oven. 190°C for 12 mins.');
INSERT INTO `preparationstep` VALUES (37, 5, 'Slice the lamb.');
INSERT INTO `preparationstep` VALUES (38, 0, 'Whisk the eggs.');
INSERT INTO `preparationstep` VALUES (38, 1, 'Cut the chilies and blossoms, then grate the galangal.');
INSERT INTO `preparationstep` VALUES (38, 2, 'Add oil into your pan.');
INSERT INTO `preparationstep` VALUES (38, 3, 'Fry your spices first.');
INSERT INTO `preparationstep` VALUES (38, 4, 'Pour the eggs in and scramble them.');
INSERT INTO `preparationstep` VALUES (38, 5, 'Add the rice in and mix it with eggs.');
INSERT INTO `preparationstep` VALUES (38, 6, 'Add Samba and Rendang. Fry them up.');
INSERT INTO `preparationstep` VALUES (39, 0, 'Mix the butter, garlic and parsley together.');
INSERT INTO `preparationstep` VALUES (39, 1, 'Season it with Touch of Taste');
INSERT INTO `preparationstep` VALUES (39, 2, 'Pour oil into your pan.');
INSERT INTO `preparationstep` VALUES (39, 3, 'Put shrimps into the pan for 60 seconds.');
INSERT INTO `preparationstep` VALUES (39, 4, 'Add your butter into the pan.');
INSERT INTO `preparationstep` VALUES (39, 5, 'Fry them.');
INSERT INTO `preparationstep` VALUES (40, 0, 'Put the grass carp slices into a bowl. Add the egg white, cooking wine, starches and salt into the bowl. Stir them and pickle them for 20 minutes.');
INSERT INTO `preparationstep` VALUES (40, 1, 'Add adequate amount of oil into the pan and heat it for a while. Put garlic broad-bean sauce into the pan. Cook them until the smell comes out.');
INSERT INTO `preparationstep` VALUES (40, 2, 'Chop the pickled cabbage into slices and cook it. Add enough water into the pot and heat it.');
INSERT INTO `preparationstep` VALUES (40, 3, 'When the water in pot is boiled, add needle mushroom into the pot.');
INSERT INTO `preparationstep` VALUES (40, 4, 'Finally, add the prepared grass carp slices into the pot. This process must be done piece by piece. Keep all the slices separated when cooking. When the color of fish changes, the dish is ready to serve.');
INSERT INTO `preparationstep` VALUES (41, 0, 'Crack the eggs into a bowl. Stir them until the egg white and yolk are mixed together.');
INSERT INTO `preparationstep` VALUES (41, 1, 'Chop the tomatoes into slices.');
INSERT INTO `preparationstep` VALUES (41, 2, 'Heat the pan and oil. When it’s ready, put the egg mixture into the pan. Fry it until it is ready. Put it aside for later use.');
INSERT INTO `preparationstep` VALUES (41, 3, 'Add tomatoes and salt into the pan. When the tomatoes become soft, add the fried eggs into the pot. Stir them together.');
INSERT INTO `preparationstep` VALUES (41, 4, 'Finally, add some green onion. Cook them for seconds and the dish is ready.');
INSERT INTO `preparationstep` VALUES (43, 0, 'Cut the pumpkin into slices. Steam the slices in a pot until it become soft.');
INSERT INTO `preparationstep` VALUES (43, 1, 'Put the pumpkin slices into the food processor. Liquidize the pumpkin into puree.');
INSERT INTO `preparationstep` VALUES (43, 2, 'Add water to the pumpkin puree and cook it until the mixture is boiled.');
INSERT INTO `preparationstep` VALUES (43, 3, 'Mix the glutinous rice flour with water. Stir them until it become thick.');
INSERT INTO `preparationstep` VALUES (43, 4, 'Add the mixture into the pumpkin puree. Put rock candy into it and cook for 2 minutes. ');
INSERT INTO `preparationstep` VALUES (43, 5, 'Wait until the pumpkin porridge cools down.');
INSERT INTO `preparationstep` VALUES (44, 0, 'Cut the beef into pieces and put them in a bowl. Add salt, soy sauce, cornstarch and oil. Stir and pickle them for 10 minutes.');
INSERT INTO `preparationstep` VALUES (44, 1, 'Cut the bitter gourd into slices. Put them in boiled water for 10 seconds and then quickly put them into cold water.');
INSERT INTO `preparationstep` VALUES (44, 2, 'Add the beef into the heated pot and stir it. When it’s 70% done, add the bitter gourd and stir them together.');
INSERT INTO `preparationstep` VALUES (44, 3, 'Add a little bit soy sauce, water and cornstarch. Stir again and the dish is ready to serve.');
INSERT INTO `preparationstep` VALUES (45, 0, 'Put the broccoli in salty water for 10 minutes and wash it again.');
INSERT INTO `preparationstep` VALUES (45, 1, 'Pickle the shelled shrimps with cooking wine and salt for 10 minutes');
INSERT INTO `preparationstep` VALUES (45, 2, 'Add oil and water into the pan. After it is heated, put the broccoli in and cook it for 2 minutes. Then immediately put it into cold water.');
INSERT INTO `preparationstep` VALUES (45, 3, 'Add garlic and fresh ginger into the pan. When the smell comes out, put the shelled shrimps and salt in. Stir them for minutes.');
INSERT INTO `preparationstep` VALUES (45, 4, 'Add the broccoli into the pan. Put salt and starch in and stir for a while.');
INSERT INTO `preparationstep` VALUES (46, 0, 'Put the squid and water into the pot and cook it until it’s ready.');
INSERT INTO `preparationstep` VALUES (46, 1, 'Cut the squid into thin slices');
INSERT INTO `preparationstep` VALUES (46, 2, 'Put the peppercorn, garlic and green onion into the pan. Add broad-bean sauce and cook them util the smell comes out.');
INSERT INTO `preparationstep` VALUES (46, 3, 'Add onion and cook for a while. Then add the squid in the pan. Maximize the fire and cook for 2 minutes.');
INSERT INTO `preparationstep` VALUES (47, 0, 'Chop the tofu into 2 cm long pieces. Put the pieces in water for 15 minutes.');
INSERT INTO `preparationstep` VALUES (47, 1, 'Heat the pot. Put peanut oil and chopped beef in. Stir them until the beef changes color. Add broad-bean sauce and garlic in. When the flavor comes out, add the cooking wine and stir again.');
INSERT INTO `preparationstep` VALUES (47, 2, 'Put the meat soup in and cook until it’s boiled. Add soy sauce and salt.');
INSERT INTO `preparationstep` VALUES (47, 3, 'Put the tofu in and cook for about 5 minutes. Then add starch into it.');
INSERT INTO `preparationstep` VALUES (47, 4, 'Keep cooking it until the dish is in the middle of soup and solid.');
INSERT INTO `preparationstep` VALUES (48, 0, 'Put the salt, pepper, star anise in a pan. Cook them until the salt turn yellow.');
INSERT INTO `preparationstep` VALUES (48, 1, 'Spray them outside and inside the duck. Pickle it for about 8 hours.');
INSERT INTO `preparationstep` VALUES (48, 2, 'Put the duck into a pot. Add enough water in it. Put salt star, anise, fresh ginger and cooking wine in it. Cook it for 30 minutes.');
INSERT INTO `preparationstep` VALUES (48, 3, 'Turn the duck over. Continue cooking it for 30 minutes. Then turn off the fire. Keep the duck in the pot for another 2 hours.');
INSERT INTO `preparationstep` VALUES (48, 4, 'Cut the duck into pieces and the dish is ready to serve.');
INSERT INTO `preparationstep` VALUES (49, 0, 'Put the salty meat into the pot. Cook it until the oil comes out.');
INSERT INTO `preparationstep` VALUES (49, 1, 'Chop the bamboo shots into thin slices.');
INSERT INTO `preparationstep` VALUES (49, 2, 'Put the sliced bamboo shots in. Add salt and cooking wine in. Cook them for a while.');
INSERT INTO `preparationstep` VALUES (49, 3, 'Add water into the pot and stew for more than 40 minutes. ');
INSERT INTO `preparationstep` VALUES (51, 0, 'Add water, olive oil and vegetable stock pots into the pot. Heat them until the soup is boiling.');
INSERT INTO `preparationstep` VALUES (51, 1, 'Pour olive oil into the pan, then add cherry tomatoes and basil into it. And then pour tomato sauce into it.');
INSERT INTO `preparationstep` VALUES (51, 2, 'Put spaghetti into the soup for 8 minutes. Then drain it.');
INSERT INTO `preparationstep` VALUES (51, 3, 'Add Mozzarella into the pasta and mix them.');
INSERT INTO `preparationstep` VALUES (51, 4, 'Mix your pasta with the sauce cooked before.');
INSERT INTO `preparationstep` VALUES (60, 0, 'Wash the chicken breast and then slice it.');
INSERT INTO `preparationstep` VALUES (60, 1, 'Add smoked paprika and pepper salt, and then mix them with chicken.');
INSERT INTO `preparationstep` VALUES (60, 2, 'Heat your pan and pour olive oil into it. Then sauté the chicken.');
INSERT INTO `preparationstep` VALUES (60, 3, 'Slice all the vegetables and fry them with the chicken.');
INSERT INTO `preparationstep` VALUES (60, 4, 'Pour white wine and chicken stock into the pan. Wait for 5 minutes.');
INSERT INTO `preparationstep` VALUES (62, 0, 'Wash the beef and then slice it alongside the bone.');
INSERT INTO `preparationstep` VALUES (62, 1, 'Get your roasting tray on the heat and make sure it’s hot. Then pour olive oil into it');
INSERT INTO `preparationstep` VALUES (62, 2, 'Season the beef with black pepper salt.');
INSERT INTO `preparationstep` VALUES (62, 3, 'Put the beef into your tray. Make sure that you give the beef a really nice sear and brown it off. This step is only supposed to color the stark！');
INSERT INTO `preparationstep` VALUES (62, 4, 'Cut the garlic half slide and then put it into the roasting tray.');
INSERT INTO `preparationstep` VALUES (62, 5, 'Stir in the tomato puree');
INSERT INTO `preparationstep` VALUES (63, 0, 'Firstly, the sauce must be made. Mix the soy sauce, fermented bean curd, oyster, cooking wine, sugar, minced garlic and adequate amount of water together. Stir them until it become sticky sauce. ');
INSERT INTO `preparationstep` VALUES (63, 1, 'Clean the streaky pork and chop it into pieces which are 2-3 cm thick. Put the pork into the airtight box and add the prepared sauce into it. Pickled them for 8 hours in a fridge.');
INSERT INTO `preparationstep` VALUES (63, 2, 'Take out the Pickled Pork. Put it into the 220℃ oven. Roast it for 40 minutes.');
INSERT INTO `preparationstep` VALUES (63, 3, 'During this period, mix the honey with water. Take out the pork from the oven every 12 minutes. Brush honey water on it and put it back in the oven.');
INSERT INTO `preparationstep` VALUES (63, 4, 'Take the barbecued pork with honey out of the oven.');

-- ----------------------------
-- Table structure for recipe
-- ----------------------------
DROP TABLE IF EXISTS `recipe`;
CREATE TABLE `recipe`  (
  `recipe_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `serveamount` int NULL DEFAULT NULL,
  `preparationTime` int UNSIGNED NULL DEFAULT NULL,
  `cookingTime` int UNSIGNED NULL DEFAULT NULL,
  `imagpath` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `flavour` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`recipe_id`) USING BTREE,
  UNIQUE INDEX `name_UNIQUE`(`name`, `recipe_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of recipe
-- ----------------------------
INSERT INTO `recipe` VALUES (34, 'truffle egg sandwich', 1, 5, 30, 'images/truffle egg sandwich.png', 'salty');
INSERT INTO `recipe` VALUES (35, 'apple tarte fine', 1, 10, 30, 'images/apple tarte fine.png', 'sweet');
INSERT INTO `recipe` VALUES (37, 'herb roasted rump of lamb', 2, 20, 60, 'images/herb roasted rump of lamb.png', 'salty');
INSERT INTO `recipe` VALUES (38, '\'tata\' spicy fried rice', 2, 10, 15, 'images/spicy fried rice from Indonesia.png', 'spicy');
INSERT INTO `recipe` VALUES (39, 'shrimp with pernod and garlic butter', 1, 20, 30, 'images/shrimp with pernod and garlic butter.png', 'salty');
INSERT INTO `recipe` VALUES (40, 'fish with pickled cabbage', 2, 10, 40, 'images/fish with pickled cabbage.jpg', 'sour');
INSERT INTO `recipe` VALUES (41, 'fried eggs with tomatoes', 2, 10, 30, 'images/fried eggs with tomatoes.jpg', 'sour');
INSERT INTO `recipe` VALUES (43, 'pumpkin porridge', 3, 10, 40, 'images/pumpkin porridge.jpg', 'sweet');
INSERT INTO `recipe` VALUES (44, 'fried bitter gourd with beef', 2, 10, 20, 'images/fried bitter gourd with beef.jpg', 'bitter');
INSERT INTO `recipe` VALUES (45, 'stir-fried shelled shrimps with broccoli', 2, 5, 15, 'images/stir-fried shelled shrimps with broccoli.jpg', 'bitter');
INSERT INTO `recipe` VALUES (46, 'spicy squid', 2, 10, 20, 'images/spicy squid.jpg', 'spicy');
INSERT INTO `recipe` VALUES (47, 'mapo beancurd', 2, 10, 20, 'images/mapo beancurd.jpg', 'spicy');
INSERT INTO `recipe` VALUES (48, 'salty duck', 5, 30, 600, 'images/salty duck.jpg', 'salty');
INSERT INTO `recipe` VALUES (49, 'fried bamboo shots with salty pork', 3, 20, 60, 'images/fried bamboo shots with salty pork.jpg', 'salty');
INSERT INTO `recipe` VALUES (51, 'spaghetti sorrentina', 3, 5, 30, 'images/spaghetti sorrentina.png', 'sour');
INSERT INTO `recipe` VALUES (57, 'Master Yoda', 100, 20, 200, 'images/chef.png', 'sweet');
INSERT INTO `recipe` VALUES (60, 'stroganoff chicken', 2, 10, 15, 'images/stroganoff chicken.png', 'spicy');
INSERT INTO `recipe` VALUES (62, 'slow cooked beef short ribs', 2, 5, 180, 'images/chef.png', 'salty');
INSERT INTO `recipe` VALUES (63, 'barbecued pork in honey', 3, 20, 600, 'images/chef.png', 'sweet');

SET FOREIGN_KEY_CHECKS = 1;
