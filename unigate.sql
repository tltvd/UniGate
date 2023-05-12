-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Май 12 2023 г., 07:10
-- Версия сервера: 10.4.27-MariaDB
-- Версия PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `unigate`
--

-- --------------------------------------------------------

--
-- Структура таблицы `access_logs`
--

CREATE TABLE `access_logs` (
  `id_access` int(11) NOT NULL,
  `id_user` varchar(12) NOT NULL,
  `room_id` int(11) NOT NULL,
  `access_time` datetime NOT NULL,
  `granted` enum('yes','no') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `access_logs`
--

INSERT INTO `access_logs` (`id_access`, `id_user`, `room_id`, `access_time`, `granted`) VALUES
(1, '011204551030', 4, '2023-04-10 04:17:33', 'yes'),
(2, '011204551030', 4, '2023-04-10 05:15:46', 'yes'),
(3, '011204551030', 4, '2023-04-10 13:43:57', 'yes'),
(4, '011204551030', 4, '2023-04-10 13:44:04', 'yes'),
(5, '011204551030', 4, '2023-04-10 13:50:21', 'yes'),
(6, '011204551030', 4, '2023-04-10 13:58:12', 'yes');

-- --------------------------------------------------------

--
-- Структура таблицы `rooms`
--

CREATE TABLE `rooms` (
  `id_room` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `location` varchar(100) NOT NULL,
  `ipv4` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `rooms`
--

INSERT INTO `rooms` (`id_room`, `name`, `location`, `ipv4`) VALUES
(4, 'ST3', 'My room', '192.168.0.100'),
(6, 'Test', '516', '192.168.88.96');

-- --------------------------------------------------------

--
-- Структура таблицы `schedules`
--

CREATE TABLE `schedules` (
  `id_schedule` int(11) NOT NULL,
  `id_user` varchar(12) NOT NULL,
  `id_room` int(11) NOT NULL,
  `day` enum('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `access_description` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `schedules`
--

INSERT INTO `schedules` (`id_schedule`, `id_user`, `id_room`, `day`, `start_time`, `end_time`, `access_description`) VALUES
(1, '010203451030', 4, 'Monday', '10:00:00', '11:00:00', ''),
(2, '020722550276', 4, 'Tuesday', '12:00:00', '13:00:00', ''),
(3, '960102551030', 4, 'Monday', '10:00:00', '11:00:00', ''),
(12, '010203451030', 4, 'Monday', '00:00:00', '10:00:00', 'test'),
(13, '010203451030', 4, 'Friday', '12:00:00', '20:00:00', 'Test'),
(14, '960102551030', 6, 'Monday', '10:00:00', '12:00:00', 'Test'),
(15, '960102551030', 6, 'Tuesday', '22:00:00', '23:59:00', 'Test'),
(16, '960102551030', 6, 'Tuesday', '22:00:00', '23:59:00', 'Test');

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

CREATE TABLE `users` (
  `id_user` varchar(12) NOT NULL,
  `role` enum('admin','teacher','user','student') NOT NULL,
  `username` varchar(20) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `password` varchar(64) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `birthdate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`id_user`, `role`, `username`, `first_name`, `last_name`, `email`, `phone`, `password`, `gender`, `birthdate`) VALUES
('010203451030', 'admin', 'test', 'test', 'test', '281276@kz.koz', '87972184470', 'i5HPwTORJ8cBjpChSM9oRB9YwLxuVsNl9soo3v6mUfo=', 'Male', '2001-02-03'),
('011204551030', 'admin', 'admin', 'Administrator', 'admin', 'admin@iitu.kz', '87772184470', 'admin', 'male', '2001-12-04'),
('020722550276', 'user', 'Alibek', 'Alibek', 'Saginbayev', '27023@iitu.edu.kz', '87770652335', 'Alibek', 'Male', '2002-07-22'),
('400706564510', 'admin', 'nur', 'Nursultan', 'Nazarbayev', 'Nazarbayev@gmail.com', '87777777777', 'U1KphLlABOUYGkxffuHoharCQvxdYnRXF0eKLDInQJI=', 'Male', '2040-07-06'),
('960102551030', 'admin', 'tlt', 'tlt', 'tlt', 'tlt@kz.kz', '87572184470', '94U+ETmUJ93G7rIw5iuc1SgxgfoprLgboy4ABfS9O6w=', 'Male', '1996-01-02');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `access_logs`
--
ALTER TABLE `access_logs`
  ADD PRIMARY KEY (`id_access`),
  ADD KEY `room_id` (`room_id`),
  ADD KEY `access_logs_ibfk_1` (`id_user`);

--
-- Индексы таблицы `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`id_room`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Индексы таблицы `schedules`
--
ALTER TABLE `schedules`
  ADD PRIMARY KEY (`id_schedule`),
  ADD KEY `room_id` (`id_room`),
  ADD KEY `schedules_ibfk_1` (`id_user`);

--
-- Индексы таблицы `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `access_logs`
--
ALTER TABLE `access_logs`
  MODIFY `id_access` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT для таблицы `rooms`
--
ALTER TABLE `rooms`
  MODIFY `id_room` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT для таблицы `schedules`
--
ALTER TABLE `schedules`
  MODIFY `id_schedule` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `access_logs`
--
ALTER TABLE `access_logs`
  ADD CONSTRAINT `access_logs_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`),
  ADD CONSTRAINT `access_logs_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id_room`) ON DELETE CASCADE;

--
-- Ограничения внешнего ключа таблицы `schedules`
--
ALTER TABLE `schedules`
  ADD CONSTRAINT `schedules_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`),
  ADD CONSTRAINT `schedules_ibfk_2` FOREIGN KEY (`id_room`) REFERENCES `rooms` (`id_room`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
