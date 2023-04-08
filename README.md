# SalaryCalculator

 This is a CRUD Spring application which allows you to manage your work days as a delivery driver.

## Technologies

* Java
* Gradle
* Spring
* H2 database
* HTML
* CSS

## Installation

1. Clone the repository

```
git clone https://github.com/username/SalaryCalculator.git
```

2. Build the project

```
gradle build
```

3. Start the application

```
gradle bootRun
```

4. Type localhost:8080/calculator in your web browser

## Usage

This application can perform basic CRUD tasks like inserting, deleting, and updating people's data through user interface. After adding record, which consists of 
date, start time, end time, number of orders, and annotation, the app displays additional information, such as: shift duration, OPH (orders per hour), hourly pay
(which is calculated using special formula), and daily earnings. On top of that, you can also check overall statistics, including total duration, orders and 
earnings, as well as average OPH, and hourly pay.

## Preview

![1](https://user-images.githubusercontent.com/100945601/230721251-19cbacd7-dee8-4294-b35a-c9330b98b980.png)
![2](https://user-images.githubusercontent.com/100945601/230722244-186799c9-a7b5-43af-9aae-9f3e06ce9eac.png)
