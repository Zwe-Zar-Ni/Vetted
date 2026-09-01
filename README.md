# Vetted

## Overview

Vetted is a personal finance and deliberate consumption app designed to curb impulse buying. Instead of immediately purchasing desired items, Vetted introduces a structured cool-off pipeline—moving items from a initial wishlist to a ready state before purchase. The application tracks user desire ratings, monitors spending analytics, and enforces spending discipline through data-driven insights and interactive home screen widgets.

## Problem Statement

Impulse purchasing often leads to financial waste and buyer's remorse. Most wishlist applications act as plain shopping catalogs, encouraging immediate checkouts without offering reflection time or behavioral analytics.

Vetted solves this by:

* Introducing mandatory reflection periods ("cool-off" stages) before items can be marked ready for purchase.
* Providing conversion metrics that highlight which impulse items are actually worth buying versus those that lose appeal over time.
* Tracking spending patterns across custom categories and monthly timelines.
* Giving quick visibility into current pipeline metrics directly from the home screen.

## Key Features

* Pipeline Management: Track items across defined lifecycle stages (WISHLISTED, READY, PURCHASED, CANCELED, RETIRED, etc.).
* Behavioral Analytics:
* Impulse Cool-Off Duration: Average days an item sits in READY status before purchase.
* Conversion Rate: Percentage of wishlisted items actually bought versus canceled or abandoned.
* Desire Rating vs. Purchase Rate: Metric showing how desire levels (1-10) correlate with actual purchases.
* Category & Monthly Spending Trends: Financial distribution across custom categories and month-over-month expenditure charts.


* Home Screen Widgets: Glance-powered widgets providing pipeline totals, ready counts, and quick actions to add items without opening the full application.

## Tech Stack

* Language: Kotlin
* UI Framework: Jetpack Compose
* Architecture: Clean Architecture with Repository Pattern and MVVM
* Dependency Injection: Koin (Core, Android, Jetpack Compose)
* Database: Room with KSP
* Navigation: Jetpack Navigation Compose with Kotlinx Serialization (Type-Safe Navigation)
* Image Loading: Coil 3 (Compose & OkHttp)
* Home Screen Widgets: Jetpack Glance (AppWidget & Material 3 interop)
* Data Visualization: Compose Charts

