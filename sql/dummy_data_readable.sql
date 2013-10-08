INSERT INTO Unit VALUES
	('ANHB4000', 'Honours human biology', 6),
	('NEUR4000', 'Honours neuro', 6),
	('PHYL4000', 'Honours physiology ', 12),
	('BIOM4000', 'Honours biomed', 12);
	
INSERT INTO Discipline VALUES
	('ANHB', 'ANHB4000'),
	('ANHB', 'NEUR4000'),
	('ANHB', 'PHYL4000'),
	('BIOMS', 'ANHB4000'),
	('BIOMS', 'NEUR4000'),
	('BIOMS', 'BIOM4000');
	
INSERT INTO Student VALUES 
	(20001234,'Bob', 'Jones', 'Mr', 'How to succeed in business without really trying', 'ANHB', 0, null),
	(20005678,'Harry', 'Smith', 'Mr', 'The cultural effect of the paradigm of mental health in modern society', 'ANHB', 0, null),
	(20112233,'Lisa', 'George', 'Ms', 'How to make sick people better', 'BIOMS', 0, null);
			
INSERT INTO Staff VALUES
	(100012345,'Lee', 'Black'),
	(100067890,'Stephanie', 'Myer'),
	(100055555,'Ronald', 'Bruce'),
	(100066666,'Mean', 'Marker');
	
INSERT INTO Supervises VALUES
	(20001234, 100012345),
	(20005678, 100012345),
	(20112233, 100067890),
	(20112233, 100055555);
	
INSERT INTO Assessment(AssessmentName, UnitCode, UnitPercent) VALUES
	('Essay', 'ANHB4000', 40),
	('Presentation', 'ANHB4000', 60),
	('Research assignment', 'NEUR4000', 30),
	('Examination', 'NEUR4000', 70),
	('Research project', 'PHYL4000', 50),
	('Dissertation', 'PHYL4000', 50),
	('Dissertation', 'BIOM4000', 100);
	
INSERT INTO SubAssessment(SubAssessmentName, AssessmentID, AssessmentPercent, MaxMarks) VALUES
	('Essay', 1, 80, 25),
	('References', 1, 20, 20),
	('Content', 2, 70, 50),
	('Delivery', 2, 30, 30),
	('Assignment', 3, 50, 100),
	('References', 3, 50, 100),
	('Examination', 4, 100, 100),
	('Project', 5, 100, 100),
	('Dissertation', 6, 100, 100),
	('Dissertation', 7, 100, 100);
	
INSERT INTO SubAssessmentMark  VALUES
	(20, 100012345, 20112233, 1, 1, 'blaaaaaank'),
	(18, 100067890, 20001234, 1, 1, 'blaaaaaank'),
	(19, 100055555, 20001234, 1, 1, 'blaaaaaank'),
	(5, 100066666, 20001234, 1, 1, 'blaaaaaank'),
	(17, 100012345, 20112233, 2, 1, 'blaaaaaank'),
	(16, 100067890, 20001234, 2, 1, 'blaaaaaank'),
	(15, 100055555, 20001234, 2, 1, 'blaaaaaank'),
	
	(35, 100012345, 20112233, 3, 1, 'blaaaaaank'),
	(42, 100067890, 20001234, 3, 1, 'blaaaaaank'),
	(41, 100055555, 20001234, 3, 1, 'blaaaaaank'),
	(29, 100012345, 20112233, 4, 1, 'blaaaaaank'),
	(26, 100067890, 20001234, 4, 1, 'blaaaaaank'),
	(25, 100055555, 20001234, 4, 1, 'blaaaaaank'),
	(24, 100066666, 20001234, 4, 1, 'blaaaaaank'),
	
	(80, 100055555, 20001234, 5, 1, 'blaaaaaank'),
	(82, 100055555, 20001234, 6, 1, 'blaaaaaank'),
	(78, 100055555, 20001234, 7, 1, 'blaaaaaank'),
	
	(69, 100055555, 20001234, 8, 1, 'blaaaaaank'),
	(81, 100055555, 20001234, 9, 1, 'blaaaaaank'),
	(81, 100012345, 20112233, 10, 1, 'blaaaaaank');