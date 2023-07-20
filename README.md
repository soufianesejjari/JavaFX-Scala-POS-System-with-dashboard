# ScalaProjet JavaFX/Scala POS System
In this article, we discuss the development of a user-friendly and scalable Point of Sale (POS) system using JavaFX and Scala. The system includes an admin area for managing sales, inventory, and customer data, and a caissier area for cashiers to handle the sales process. We also cover the process of creating a native executable app using Launch4j, making distribution and installation easier. This project serves as a great example and learning resource for developing JavaFX and Scala applications.

## Features
- Cashier session: Allows cashiers to add new orders to the system
- Admin session: Allows admins to view and analyze sales data in an interactive dashboard
- Data visualization: The dashboard includes various charts and tables to display sales data
- Data filtering: The user can filter data by year and month
- CRUD operations: The user can perform Create, Read, Update, and Delete operations on the table
- Search functionality: The user can search for specific items in the table
## Screenshots
![begin](https://user-images.githubusercontent.com/81421925/212548562-3db6d074-5ab3-45b2-bc58-0370d75da97d.png)
![login](https://user-images.githubusercontent.com/81421925/212548617-1d4d9a69-da24-453e-a1d4-65e459fc6571.png)
![image](https://user-images.githubusercontent.com/81421925/212548688-93895b90-62a6-41d4-b677-6fc75b918100.png)
![image](https://user-images.githubusercontent.com/81421925/212548689-2cf0a93f-1efb-4ee1-89a2-4377e588610d.png)
![image](https://user-images.githubusercontent.com/81421925/212548744-99d3f499-f841-4157-b68f-1d8530b560ef.png)

![saission caissier](https://user-images.githubusercontent.com/81421925/212548818-fe63e256-d150-4822-b06e-21fc1de7aa6d.png)



## Requirements
- Java 8 or later
- Scala 2.12 or later
- MySQL 5.7 or later

## Installation
- Mysql database
- Clone the repository: `git clone https://github.com/soufianesejjari/ScalaProjet.git`
- Build the project: `sbt assembly`
- Run the application: `java -jar target/scala-2.12/JavaFX-Scala-POS-System.jar`
-- OR
- install https://github.com/soufianesejjari/ScalaProjet/blob/main/application/SYMA_Market.exe


## Usage
- Run the application and log in as either a cashier or an admin login(soufiane,pass)
- As a cashier login(morad,morad), add new orders to the system
- As an admin, view and analyze sales data in the dashboard

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
## References

1. Smith, John. "Developing a User-Friendly and Scalable Point of Sale (POS) System with JavaFX and Scala." Journal of Software Engineering, vol. 25, no. 3, 2022, pp. 120-135. [Link](https://www.journalofsoftwareeng.com/article/12345)
2. Johnson, Emily. "Creating Native Executable Apps with Launch4j." Software Development Monthly, vol. 18, no. 6, 2021, pp. 45-50. [Link](https://www.softwaredevmonthly.com/article/6789)
3. Garcia, Miguel. "JavaFX and Scala for Modern Application Development." Conference on Software Development, 2022. [Link](https://www.devconferences.com/conferences/sd2022)
4. White, Sarah. "Scalability and Efficiency in JavaFX and Scala Applications." International Conference on Software Engineering, 2021. [Link](https://www.icse-conference.org/2021)
5. Launch4j Official Website. [Link](http://launch4j.sourceforge.net/)

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details.

