# **Meeting Room Reservation System**




## Project scope
The project is aimed at larger companies with a large number of employees or companies where meetings are held regularly.

## Overall Description
**Meeting Room Reservation System** allows to manage and reserve meeting rooms in offices. It will also support room prioritization, meaning that some rooms can be booked when others are already occupied at a given time, or a staff member with a higher priority than others will be able to book them. This priority can be set in the system. It will also display the attributes of each room, such as maximum capacity and available equipment. Mainly this system will be for corporations that have a large number of employees to organize the work in the office. The output of this project will be a Browser-based Meeting Room Reservation System.

## ER diagram: 
![ER diagram of database](/diagrams/er.png "ER diagram of database")

## User class and characteristics
Roles in system: admin, employee.

**admin:**
- set priority of employee;
- add new employee to the system;
- set priority of meeting rooms;
- add equipment to meeting rooms;
- edit room attributes;
- cancellation of an employee's reservation.

**employee:**
- reservation of the meeting room;
- cancellation of the reservation;
- display room attributes;
- automatic room reservation for permanent meetings.

## System limitations

Meeting Room Reservation System is designed for easy booking of meeting rooms within one company. However, the system will not support user ratings. This means that we will not avoid, for example, users who have once damaged our property.
Also, the system does not allow one employee to book two different rooms for the same day and time. For example, if an employee needs a larger room due to a change in the number of invited guests, it must first cancel the first reservation (it still depends on whether the cancellation is made within one day of the meeting), then book another, larger room.

## Nonfunctional requirements

Performance Requirements:
- The system should be operational 24 hours a day to allow user interaction at any time.

Security requirements:
- The administrator should maintain the database carefully; any loss can lead to chaos. Prevention of false IDs.
- Administrator password must be kept strictly confidential. User ID must be kept confidential.
- Reservations can only be made by staff working in the building where the room is located. 

## Installation instructions

1.  Clone project.
2.	Setting application.properties for database access.
3.	We don't provide test data, you have to create it yourself.

