--password: sayedbaladoh
MERGE INTO `users` (`id`, `name`, `username`, `email`, `password`, `created_at`, `updated_at`) VALUES
	(1, 'Sayed Baladoh', 'sayedbaladoh', 'sayedbaladoh@yahoo.com', '$2a$10$psTNGV871DN4s5gNkrzTfu.uYJSu2btu2Xt3gjVm8aYSh8L/Gvvfa', '2018-08-12 15:41:59', '2018-08-12 15:41:59');

MERGE INTO `brands` (`id`, `name`, `website`, `created_at`, `updated_at`) VALUES
	(1, 'BMW', 'www.bmw.com', '2018-08-12 15:41:59', '2018-08-12 15:41:59'),
	(2, 'Ford', '', '2018-08-12 15:43:05', '2018-09-20 13:16:27'),
	(3, 'Toyota', '', '2018-08-15 13:51:01', '2018-08-15 13:51:01');

MERGE INTO `models` (`id`, `name`, `brand_id`, `year_from`, `year_To`, `created_at`, `updated_at`) VALUES
	(1, '1 Series', 1, 2007, 2012 , '2018-08-12 15:41:59', '2018-08-12 15:41:59'),
	(2, 'Aspire', 2, 1994, 1998,'2018-08-12 15:43:05', '2018-09-20 13:16:27'),
	(3, '2 Series', 1, 2013, 2017, '2018-08-15 13:51:01', '2018-08-15 13:51:01'),
	(4, 'C-Max', 2, 2003, 2007,'2018-08-15 13:51:01', '2018-08-15 13:51:01'),
	(5, 'Bronco', 2, 1992, 1998, '2018-08-15 13:51:01', '2018-08-15 13:51:01');

MERGE INTO `body_types` (`id`, `name`, `created_at`, `updated_at`) VALUES
	(1, 'Saloon', '2018-08-12 15:41:59', '2018-08-12 15:41:59'),
	(2, 'Cabrio', '2018-08-12 15:43:05', '2018-09-20 13:16:27'),
	(3, 'Estate', '2018-08-15 13:51:01', '2018-08-15 13:51:01'),
	(4, 'Hatchback', '2018-08-15 13:51:01', '2018-08-15 13:51:01'),
	(5, 'Coupe', '2018-08-15 13:51:01', '2018-08-15 13:51:01'),
	(6, 'Liftback', '2018-08-15 13:51:01', '2018-08-15 13:51:01'),
	(7, 'MPV', '2018-08-15 13:51:01', '2018-08-15 13:51:01');

MERGE INTO `specifications` (`id`, `model_id`, `body_type_id`, `doors_no`, `seats_no`, `length`, `width`, `height`, `created_at`, `updated_at`) VALUES
	(1, 1, 1, 4, 4, 4520, 1817, 1421, '2018-08-12 15:41:59', '2018-08-12 15:41:59'),
	(2, 1, 2, 4, 4, 4520, 1817, 1421, '2018-08-12 15:43:05', '2018-09-20 13:16:27'),
	(3, 1, 3, 3, 4, 4520, 1817, 1421, '2018-08-15 13:51:01', '2018-08-15 13:51:01'),
	(4, 2, 5, 4, 5, 4520, 1817, 1421, '2018-08-15 13:51:01', '2018-08-15 13:51:01'),
	(5, 2, 1, 4, 4, 4520, 1817, 1421, '2018-08-15 13:51:01', '2018-08-15 13:51:01'),
	(6, 3, 7, 2, 4, 4520, 1817, 1421, '2018-08-15 13:51:01', '2018-08-15 13:51:01'),
	(7, 4, 4, 4, 5, 4520, 1817, 1421, '2018-08-15 13:51:01', '2018-08-15 13:51:01');

	
MERGE INTO roles(`id`, `name`) VALUES
	(1, 'ROLE_USER'),
	(2, 'ROLE_ADMIN');