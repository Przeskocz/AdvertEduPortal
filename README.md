# The AdvertEdu Portal application
It is a web application locating services for students. The main functionalities are user registration and login, adding their own ads as well as searching and browsing available ones. In addition, its additional advantage is the categorization of added ads. The application is available from a web browser.

## Technologies
 - Java 8/11
 - Spring Boot
 - Maven
 - Hibernate
 - H2 database
 - Thymeleaf
 - Ajax

## Range of URL Mappings
|  | URL | Method | Is authorized? | Description
|--|--|--|--|--|
| 1 | / | GET | No | Returns the page view homepage. |
| 2 | /home | GET | No | Returns the page view homepage. |
| 3 | /login | GET | No | Returns the login and registration form. |
| 4 | /registration | GET | No | Returns the login and registration form. |
| 5 | /successlogout | GET | No | As above but used when logging out correctly. |
| 6 | /registration | POST | No | Receives data for registration of a new user. |
| 7 | /user/advertisements | GET | No | Returns a list of all actual ads.  |
| 8 | /user/advertisements/edit/{id} | GET | Yes | Returns the advertisement with the specified {id} for the edit form. |
| 9 | /user/advertisements/edit/{id} | POST | Yes | Receives a request with {id} to update the ad. |
| 10 | /user/advertisements/delete/{id} | GET | Yes | Request removal of the given ad number {id}. |
| 11 | /user/advertisements/image/delete/{id} | GET | Yes | Receives a request to remove the image with the specified {id} from the ad. |
| 12 | /user/advertisements/new | POST | Yes | Receives data to register a new announcement. |
| 13 | /user/advertisements/new | GET | Yes | Returns the registration form for a new advertisement. |
| 14 | /advertisement/list | GET | No | Returns a list of all current ads. |
| 15 | /advertisement/{id} | GET | No | Returns the details of an ad with the given {id}. |
| 16 | /advertisement/university/{name} | GET | No | Returns a list of current university-related ads with the given {name}. |
| 17 | /advertisement/list/{type} | GET | No | Returns a list of current ones ads assigned to category {type}. |

## Run app
The application requires compilation of sources and placing on the Java server, e.g. Tomcat. The application demo was additionally deployed on the [Heroku platform](https://prz-advert-edu-portal.herokuapp.com/). 
There are already registered users in the system:

 - Administrator account:
Login: *skoczp@gmail.com*
Password: *qwertyasdfg*

- User account:
Login: *zwykly@user.pl*
Hasło: *qwerty*
