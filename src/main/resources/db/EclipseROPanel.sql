CREATE TABLE `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(50),
  `nick` varchar(50),
  `hash_password` varchar(255),
  `discord_id` varchar(255),
  `discord_nick` varchar(255),
  `create_timestamp` long,
  `last_login` long
);

CREATE TABLE `accounts` (
  `ro_id` int PRIMARY KEY,
  `user_id` int,
  `timestamp` long
);

CREATE TABLE `character` (
  `ro_id` int PRIMARY KEY,
  `name` varchar(23),
  `account_id` int,
  `timestamp` long
);

CREATE TABLE `character_info` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `character_id` int,
  `class_id` int,
  `base_lvl` int,
  `job_lvl` int,
  `sex` ENUM ('male', 'female'),
  `head_type` int,
  `head_color` int,
  `clothes_color` int,
  `stat_str` int,
  `stat_agi` int,
  `stat_vit` int,
  `stat_int` int,
  `stat_dex` int,
  `stat_luk` int,
  `character_skills` text,
  `last_update` long
);

CREATE TABLE `items_db` (
  `ro_id` int PRIMARY KEY,
  `type` ENUM ('usable', 'equip', 'etc'),
  `equip_position` ENUM ('head_top', 'head_mid', 'head_low', 'head_top_mid', 'head_top_low', 'head_mid_low', 'head_top_mid_low', 'armor', 'hand_r', 'hand_l'),
  `name` varchar(50),
  `description` text,
  `total_cart` int,
  `enchant_enable` boolean,
  `is_custom` boolean
);

CREATE TABLE `character_item_ob` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `item_id` int,
  `refine` int,
  `carts` text,
  `enchants` text,
  `very_very_count` int
);

CREATE TABLE `character_item` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `character_ro_id` int,
  `item_ob` int
);

CREATE TABLE `skills_db` (
  `ro_id` int PRIMARY KEY,
  `class_id` int,
  `name` varchar(50),
  `description` text,
  `dependence` text
);

CREATE TABLE `playable_class` (
  `ro_id` int PRIMARY KEY,
  `name` varchar(50),
  `father` int,
  `skills` text
);

CREATE TABLE `character_equip_set` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `character_ro_id` int,
  `items_ob` text
);

CREATE TABLE `character_wanted_item` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `character_ro_id` int,
  `item_ob` int,
  `timestamp` long
);

ALTER TABLE `accounts` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `character` ADD FOREIGN KEY (`account_id`) REFERENCES `accounts` (`ro_id`);

ALTER TABLE `character_info` ADD FOREIGN KEY (`character_id`) REFERENCES `character` (`ro_id`);

ALTER TABLE `character_info` ADD FOREIGN KEY (`class_id`) REFERENCES `playable_class` (`ro_id`);

ALTER TABLE `character_item_ob` ADD FOREIGN KEY (`item_id`) REFERENCES `items_db` (`ro_id`);

ALTER TABLE `character_item` ADD FOREIGN KEY (`character_ro_id`) REFERENCES `character` (`ro_id`);

ALTER TABLE `character_item` ADD FOREIGN KEY (`item_ob`) REFERENCES `character_item_ob` (`id`);

ALTER TABLE `skills_db` ADD FOREIGN KEY (`class_id`) REFERENCES `playable_class` (`ro_id`);

ALTER TABLE `playable_class` ADD FOREIGN KEY (`father`) REFERENCES `playable_class` (`ro_id`);

ALTER TABLE `character_equip_set` ADD FOREIGN KEY (`character_ro_id`) REFERENCES `character` (`ro_id`);

ALTER TABLE `character_wanted_item` ADD FOREIGN KEY (`character_ro_id`) REFERENCES `character` (`ro_id`);

ALTER TABLE `character_wanted_item` ADD FOREIGN KEY (`item_ob`) REFERENCES `character_item_ob` (`id`);
