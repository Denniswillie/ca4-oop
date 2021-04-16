DROP TABLE IF EXISTS student_courses;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS course;

CREATE TABLE `course` (
  `courseid` varchar(10) NOT NULL,
  `level` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `institution` varchar(30) NOT NULL,
    PRIMARY KEY(courseid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `course` (`courseid`, `level`, `title`, `institution`) VALUES
('DK135', 8, 'Computing', 'Dundalk Instituition');
INSERT INTO `course` (`courseid`, `level`, `title`, `institution`) VALUES
('DK136', 8, 'Maths', 'MIT');
INSERT INTO `course` (`courseid`, `level`, `title`, `institution`) VALUES
('DK137', 8, 'Physics', 'UC Berkeley');
INSERT INTO `course` (`courseid`, `level`, `title`, `institution`) VALUES
('DK138', 8, 'Biology', 'Dundalk Instituition');
INSERT INTO `course` (`courseid`, `level`, `title`, `institution`) VALUES
('DK139', 8, 'Chemistry', 'MIT');
INSERT INTO `course` (`courseid`, `level`, `title`, `institution`) VALUES
('DK140', 8, 'Topology', 'UC Berkeley');

CREATE TABLE `student` (
  `caoNumber` int(11) NOT NULL,
  `date_of_birth` varchar(10) NOT NULL,
  `password` varchar(30) NOT NULL,
    PRIMARY KEY(caoNumber)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `student` (`caoNumber`, `date_of_birth`, `password`) VALUES
(123456, '1990-12-30', 'user123456');

CREATE TABLE `student_courses` (
  `caoNumber` int(11) NOT NULL,
  `courseid` varchar(10) NOT NULL,
    PRIMARY KEY(caoNumber, courseid),
    FOREIGN KEY(caoNumber)REFERENCES student(caoNumber),
    FOREIGN KEY(courseid) REFERENCES course(courseid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `student_courses` (`caoNumber`, `courseid`) VALUES
(123456, 'DK135'),
(123456, 'DK136'),
(123456, 'DK137');
