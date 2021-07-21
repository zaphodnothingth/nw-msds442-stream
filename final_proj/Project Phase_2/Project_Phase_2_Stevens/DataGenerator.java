package bbejeck.util.datagen;

import bbejeck.model.BeerPurchase;
import bbejeck.model.ClickEvent;
import bbejeck.model.Currency;
import bbejeck.model.PublicTradedCompany;
import bbejeck.model.Purchase;
import bbejeck.model.StockTransaction;
import com.github.javafaker.ChuckNorris;
import com.github.javafaker.Faker;
import com.github.javafaker.Finance;
import com.github.javafaker.Name;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import java.io.FileWriter;
import java.io.*;
import java.util.stream.Collectors;
import java.util.*;
import java.lang.*;

import bbejeck.chapter_3.WarehouseDistributionCenter;

 
public class DataGenerator {

    public static final int NUMBER_UNIQUE_CUSTOMERS = 100;
    public static final int NUMBER_UNIQUE_WDCS = 15;
    public static final int NUMBER_TEXT_STATEMENTS = 15;
    public static final int DEFAULT_NUM_PURCHASES = 100;
    public static final int NUMBER_TRADED_COMPANIES = 50;
    public static final int NUM_ITERATIONS = 100;
	public static final int TOTAL_NUM_PURCHASES = DEFAULT_NUM_PURCHASES * NUM_ITERATIONS;

    private static Faker dateFaker = new Faker();
    private static Supplier<Date> timestampGenerator = () -> dateFaker.date().past(15, TimeUnit.MINUTES, new Date());


//////////////////////////////////////////////////////////
//////////// 			For OnMart App       ///////////// 

	
	public static int purchasesGenerated = 0;
	
	public static ArrayList<String> zipCodesValuesList = new ArrayList<String>();
	
	public static Map<String, WarehouseDistributionCenter> wdcMap = new HashMap<>();

	public static HashMap<Integer, String> customersList = new HashMap<Integer, String>();






    private DataGenerator() {
    }



    public static void setTimestampGenerator(Supplier<Date> timestampGenerator) {
        DataGenerator.timestampGenerator = timestampGenerator;
    }


    public static List<String> generateRandomText() {
        List<String> phrases = new ArrayList<>(NUMBER_TEXT_STATEMENTS);
        Faker faker = new Faker();

        for (int i = 0; i < NUMBER_TEXT_STATEMENTS; i++) {
            ChuckNorris chuckNorris = faker.chuckNorris();
            phrases.add(chuckNorris.fact());
        }
        return phrases;
    }

    public static List<String> generateFinancialNews() {
        List<String> news = new ArrayList<>(9);
        Faker faker = new Faker();
        for (int i = 0; i < 9; i++) {
            news.add(faker.company().bs());
        }
        return news;
    }


    public static List<ClickEvent> generateDayTradingClickEvents(int numberEvents, List<PublicTradedCompany> companies) {
        List<ClickEvent> clickEvents = new ArrayList<>(numberEvents);
        Faker faker = new Faker();
        for (int i = 0; i < numberEvents; i++) {
            String symbol = companies.get(faker.number().numberBetween(0,companies.size())).getSymbol();
            clickEvents.add(new ClickEvent(symbol, faker.internet().url(),timestampGenerator.get().toInstant()));
        }

        return clickEvents;
    }

    public static Purchase generatePurchase() {
        return generatePurchases(1, 1).get(0);
    }



//////////////////////////////////////////////////////////
//////////// 			For OnMart App       ///////////// 

    public static List<Purchase> generatePurchases(int number, int numberCustomers) {
        List<Purchase> purchases = new ArrayList<>();

        Faker faker = new Faker();
        List<Customer> customers = generateCustomers(numberCustomers);
        Random random = new Random();
		


        for (int i = 0; i < number; i++) {

			
			String itemPurchased, orderId, productId, city, state, zipCode, distrCenterId;
			double price, discount, shippingCost;
			int wdc_location;
			
			String department = generateDepartment();
			String category = generateCategory(department);
			

			if(department == "Office Supplies")
			{
				if(category == "Appliances")
				{
					itemPurchased = faker.options().option("KitchenAid Microwave - White","Breville Refrigerator - Red","Hoover Stove - Red","LG Air Conditioning - White","Onida LED 32 inch","Samsung LCD 40 inch","RFID Scanners","LG Microwave - Black","Philips Refrigerator - Blue","Pigeon Stove - Glass");
				    price = Double.parseDouble(faker.commerce().price(300.00, 400.00));
					discount = Double.parseDouble(faker.commerce().price(20.00, 25.00));
					shippingCost = Double.parseDouble(faker.commerce().price(22.00, 28.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);
					
					
				} else if(category == "Art"){
					itemPurchased = faker.options().option("Pencils - Medium Soft","Highlighter - Yellow Ink","Lumber Crayons","Pencil Sharpener - 1670","Watercolor Pencils","Brooke Multipurpose Staples","Keon Office Whiteboard","Whiteboard Marker Set - 10 pcs","Office Art Frames","Office Calendar Maps");
					price = Double.parseDouble(faker.commerce().price(5.00, 10.00));
					discount = Double.parseDouble(faker.commerce().price(1.00, 2.00));
					shippingCost = Double.parseDouble(faker.commerce().price(1.00, 2.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				} else {
					// Binders
					itemPurchased = faker.options().option("GBC Velo Bind Cover","ACCOHIDE Binder - Blue - 1","Heavy Duty Binders","Avery Binding System","Alpha Singer Bind Cover","Tarzan Binder - Red - 5","Medium Duty Binders","Brite Binding System","Wayne Geo Bind Cover","Light Duty Binders");
					price = Double.parseDouble(faker.commerce().price(10.00, 15.00));
					discount = Double.parseDouble(faker.commerce().price(1.00, 2.00));
					shippingCost = Double.parseDouble(faker.commerce().price(2.00, 4.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				}
            } else if(department == "Electronics") {
				if(category == "Accessories"){
					itemPurchased = faker.options().option("Blu-ray Single Disc - 10/Pack","Enermax Router - Bluetooth","1TB Portable External Hard Drive","Logitech Marble Mouse","Logitech Wireless Mouse","Electronic Pen","Logitech 10 GB Pen Drive","Samsonite Electronic Wrist Watch","Cisco Router - Ethernet","Blaze Optic Mouse");
					price = Double.parseDouble(faker.commerce().price(100.00, 200.00));
					discount = Double.parseDouble(faker.commerce().price(10.00, 12.00));
					shippingCost = Double.parseDouble(faker.commerce().price(12.00, 13.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				} else if(category == "Copiers") {
					itemPurchased = faker.options().option("Sharp Wireless Fax - Laser","Hewlett Wireless Fax - Laser","HP Wireless Fax - Digital","Canon Wireless Fax - High-Speed","Sharp Fax Machine - Digital","Zoho Wireless Scanner - Automatic","HP Multipurpose Laser Scanner","HP Digital Scanner","Canon High-Speed Scanner","Sharp Machine - Scanner");
					price = Double.parseDouble(faker.commerce().price(500.00, 600.00));
					discount = Double.parseDouble(faker.commerce().price(20.00, 22.00));
					shippingCost = Double.parseDouble(faker.commerce().price(24.00, 26.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				} else if(category == "Machines"){
					itemPurchased = faker.options().option("Konica Inkjet - White","Konica Printer - Wireless","Okidata Inkjet - White","Epson Printer - Red","StarTech Card Printer - White","Samsung Inkjet - Red","LG Printer - Wireless Blue","Panasonic Laser Inkjet","HP Printer - White","Epsilon Card Laser Printer - Red");
					price = Double.parseDouble(faker.commerce().price(800.00, 1000.00));
					discount = Double.parseDouble(faker.commerce().price(50.00, 100.00));
					shippingCost = Double.parseDouble(faker.commerce().price(40.00, 60.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

				} else {
					// Phones
					itemPurchased = faker.options().option("Samsung Convoy 3","Nokia Smart Phone","Motorola Smart Phone","Samsung Smart Phone","LG CDMA 3","Panasonic Cordless Phone","LG Wireless Phone","Moto X Play","Oppo F1 Series","Redmi 4 Smart Phone");
					price = Double.parseDouble(faker.commerce().price(500.00, 800.00));
					discount = Double.parseDouble(faker.commerce().price(10.00, 20.00));
					shippingCost = Double.parseDouble(faker.commerce().price(5.00, 12.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				}
			} else if(department == "Furniture") {
				if(category == "Bookcases"){
					itemPurchased = faker.options().option("Sauder Classic Bookcase - Traditional","Safco Classic Bookcase - Pine","Ikea Classic Bookcase - Mobile","Dania Classic Bookcase - Metal","Ron Bookcase - Fashionable","Lively Bookcase - Wood","Lotto Bookcase - Remote","Lava Classic Bookcase - PVC","Glory Bookcase - Furnishings","Wheelo Bookcase - Multipurpose");
					price = Double.parseDouble(faker.commerce().price(100.00, 200.00));
					discount = Double.parseDouble(faker.commerce().price(10.00, 12.00));
					shippingCost = Double.parseDouble(faker.commerce().price(12.00, 13.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				} else if(category == "Chairs") {
					itemPurchased = faker.options().option("Novimex Leather Armchair - Adjustable","Manager's Chair - Indigo","Executive Armchair - Brown","Office Armchair - Adjustable","SAFCO Leather Armchair - Black","Latex Leather Chair - Adjustable","PVC Chair - Indigo","Pilot Armchair - Magenta","Comfy Office Chair - Dusk","Brite Armchair - Leg Comforter");
					price = Double.parseDouble(faker.commerce().price(100.00, 150.00));
					discount = Double.parseDouble(faker.commerce().price(11.00, 12.00));
					shippingCost = Double.parseDouble(faker.commerce().price(10.00, 13.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				} else if(category == "Furnishings") {
					itemPurchased = faker.options().option("Desk Accessories - Ebony","Howard Round Wall Clock","DuraMat Pile Carpeting","Floor Lamp","Poster Frame","Study Table Accessories - Dusk","Oscil Wall Clock","PVC Carpeting","Granite Flooring Mat","Wooden Glass Frame");
					price = Double.parseDouble(faker.commerce().price(200.00, 300.00));
					discount = Double.parseDouble(faker.commerce().price(10.00, 12.00));
					shippingCost = Double.parseDouble(faker.commerce().price(15.00, 20.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				} else  
				{
					// Tables
					itemPurchased = faker.options().option("Lively Conference Table - Assembled","Bevis Conference Table - Assembled","Computer Table - Bottom Storage","Chrom Wood Table - Rectangular","Chrom Round Table","PVC Table - Assembled","Brite Conference Table - Assembled","Laptop Table - Multipurpose","Elong Wooden Table - Rectangular","Frontier Mount Side Table");
					price = Double.parseDouble(faker.commerce().price(200.00, 400.00));
					discount = Double.parseDouble(faker.commerce().price(10.00, 20.00));
					shippingCost = Double.parseDouble(faker.commerce().price(10.00, 15.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				}
				
		////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////
		/////////////															////////////
		/////////////			ADD YOUR CODE HERE								////////////
		/////////////															////////////
		/////////////			Add at least 2 categories per department for:	////////////
		/////////////			1. 	grocery										////////////
		/////////////			2. 	clothing									////////////		
		/////////////			3. 	sports										////////////
		/////////////															////////////
		////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////	
				
			} else if(department == "Clothing"){
				if(category == "TShirt"){
					itemPurchased = faker.options().option("V-neck - Red","Crew-neck - Blue");
					price = Double.parseDouble(faker.commerce().price(10.00, 20.00));
					discount = Double.parseDouble(faker.commerce().price(10.00, 12.00));
					shippingCost = Double.parseDouble(faker.commerce().price(3.00, 5.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				} else  
				{
					// Shorts
					itemPurchased = faker.options().option("Running Shorts - Black","Cargo Shorts - Khaki");
					price = Double.parseDouble(faker.commerce().price(30.00, 50.00));
					discount = Double.parseDouble(faker.commerce().price(10.00, 20.00));
					shippingCost = Double.parseDouble(faker.commerce().price(5.00, 7.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				}

			} else if(department == "Sports"){
				if(category == "Fitness"){
					itemPurchased = faker.options().option("Dumbell - 10 lbs","Jump Rope");
					price = Double.parseDouble(faker.commerce().price(20.00, 25.00));
					discount = Double.parseDouble(faker.commerce().price(10.00, 12.00));
					shippingCost = Double.parseDouble(faker.commerce().price(12.00, 13.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				} else if(category == "Athletic Clothing") {
					itemPurchased = faker.options().option("Socks - ankle length","Sweatband");
					price = Double.parseDouble(faker.commerce().price(5.00, 7.00));
					discount = Double.parseDouble(faker.commerce().price(11.00, 12.00));
					shippingCost = Double.parseDouble(faker.commerce().price(3.00, 5.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				} else if(category == "Golf") {
					itemPurchased = faker.options().option("Driver - Titanium","One Dozen Golf Balls");
					price = Double.parseDouble(faker.commerce().price(25.00, 300.00));
					discount = Double.parseDouble(faker.commerce().price(10.00, 12.00));
					shippingCost = Double.parseDouble(faker.commerce().price(15.00, 20.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				} else  
				{
					// Hunting & Fishing
					itemPurchased = faker.options().option("Cast Rod - 6'","Tackle Box");
					price = Double.parseDouble(faker.commerce().price(20.00, 40.00));
					discount = Double.parseDouble(faker.commerce().price(10.00, 20.00));
					shippingCost = Double.parseDouble(faker.commerce().price(10.00, 15.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				}
				
			} else {
				// Grocery 
				if(category == "Snaks"){
					itemPurchased = faker.options().option("Potato Chips","Corn Chips");
					price = Double.parseDouble(faker.commerce().price(3.00, 5.00));
					discount = Double.parseDouble(faker.commerce().price(10.00, 12.00));
					shippingCost = Double.parseDouble(faker.commerce().price(3.00, 5.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				} else if(category == "Beans") {
					itemPurchased = faker.options().option("Black Beans","Pinto Beans");
					price = Double.parseDouble(faker.commerce().price(2.00, 3.00));
					discount = Double.parseDouble(faker.commerce().price(11.00, 12.00));
					shippingCost = Double.parseDouble(faker.commerce().price(5.00, 6.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				} else if(category == "Pasta") {
					itemPurchased = faker.options().option("Noodles - Elbow Macaroni","Lasagna Noodles");
					price = Double.parseDouble(faker.commerce().price(3.00, 5.00));
					discount = Double.parseDouble(faker.commerce().price(10.00, 12.00));
					shippingCost = Double.parseDouble(faker.commerce().price(3.00, 5.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				} else  
				{
					// Nuts
					itemPurchased = faker.options().option("Roasted Peanuts","Cashews");
					price = Double.parseDouble(faker.commerce().price(8.00, 9.00));
					discount = Double.parseDouble(faker.commerce().price(10.00, 20.00));
					shippingCost = Double.parseDouble(faker.commerce().price(5.00, 7.00));
					orderId = java.util.UUID.randomUUID().toString();
					productId = department.substring(0,3).toUpperCase() + "-" + category.substring(0,2).toUpperCase() + "-" + faker.number().digits(4) + "-" + faker.number().digits(3);

					
				}
			}
            
			int quantity = faker.number().numberBetween(1, 5);
			

            Date purchaseDate = timestampGenerator.get();
            String shippingClass = faker.options().option("Next Day", "Two Day", "Standard");
            Date deliveryDate = getDeliveryDate(shippingClass);
            String segment = faker.options().option("Consumer", "Corporate", "Home Office");
            double sales = price*quantity - discount + shippingCost;
			double profit_range = Double.parseDouble(faker.commerce().price(0.01, 0.20));
            double profit = sales * profit_range;
            String orderPriority = faker.options().option("Critical", "High", "low", "medium");
            Customer customer = customers.get(random.nextInt(numberCustomers));	
            
			
			int returnedPurchase = new Random().nextInt(5)+1;
			int purchaseDeliveryDelayed = new Random().nextInt(5)+1;
			int purchaseStatus = new Random().nextInt(5)+1;
		
			String randomZip = getRandomZipCodeFromSupplyChain();
			
			int totalPurchases = wdcMap.get(randomZip).getTotalPurchases() + 1;
			int totalPurchasesReturned = wdcMap.get(randomZip).getTotalPurchasesReturned();
			int totalPurchasesDeliveryDelayed = wdcMap.get(randomZip).getTotalPurchasesDeliveryDelayed();
			
			// Increment the number of purchases for this zipCode
			wdcMap.get(randomZip).setTotalPurchases(totalPurchases);
		

		
			String purchaseStatusLabel;
			if (purchaseStatus == 1) {
				purchaseStatusLabel = new String("Declined");
			} else {
				purchaseStatusLabel = new String("Approved");
			}
		
			String returnedPurchaseLabel;
			if (returnedPurchase == 1) {
				returnedPurchaseLabel = new String("Yes");
				// Increment the number of returned purchases for this zipCode
				wdcMap.get(randomZip).setTotalPurchasesReturned(totalPurchasesReturned+1);
			} else {
				returnedPurchaseLabel = new String("No");
			}
		
			
		////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////
		/////////////														////////////
		/////////////			ADD YOUR CODE HERE							////////////
		/////////////														////////////
		/////////////			purchaseDeliveryDelayedLabel				////////////
		/////////////			1. 	Yes										////////////
		/////////////			2. 	No										////////////		
		/////////////														////////////
		////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////	
		
			String purchaseDeliveryDelayedLabel;
			if (purchaseDeliveryDelayed == 1) {
				purchaseDeliveryDelayedLabel = new String("Yes");
				// Increment the number of returned purchases for this zipCode
				wdcMap.get(randomZip).setTotalPurchasesDeliveryDelayed(totalPurchasesDeliveryDelayed+1);
			} else {
				purchaseDeliveryDelayedLabel = new String("No");
			}
		
			
			
			
			// Random value 0 or 1 to indicate if this purchased product has been reviewed
			int purchasedProductReviewed = new Random().nextInt(2);
			int purchasedProductRating = new Random().nextInt(5);
			String purchasedProductReviewID;

						
			if (purchasedProductReviewed == 1){
				// This is making up a MOCK ReviewID
				purchasedProductReviewID = orderId.substring(3, 13);
			} else {
				purchasedProductReviewID = "";
			}
			
			
			// Generate Random number of friends
			int numberOfFriends = new Random().nextInt(5); 
			String friends = "";
			String sharedWithFriends = "";
			HashMap<String, String> friendsList = new HashMap<String, String>();
			
			customersList.put(purchasesGenerated, customer.customerId);
			List<String> valuesList = new ArrayList<String>(customersList.values());
			
			for(int j=0; (j < numberOfFriends) && (valuesList.size() > 5); j++){
				int randomIndex = new Random().nextInt(valuesList.size());
				String randomFriend = valuesList.get(randomIndex);
				if( ! randomFriend.equals(customer.customerId)){
					if( ! friendsList.containsKey(randomFriend)) {
						// Add friend to the list of friends
						friendsList.put(randomFriend, randomFriend);
						if(friends.length() > 0)
							friends = friends+ ";" + randomFriend;
						else
							friends = randomFriend;
						
						// Add friend to the list of friends shared product with
						int shared = new Random().nextInt(2);;
						if (shared == 1){
							if(sharedWithFriends.length() > 0)
								sharedWithFriends = sharedWithFriends+ ";" + randomFriend;
							else
								sharedWithFriends = randomFriend;	
						}
												
			
					}
				}
			}


			
			String country = faker.options().option("USA");
            String region = faker.options().option("TO_BE_ADDED");
			
			
            Purchase purchase = Purchase.builder()
            		.creditCardNumber(customer.creditCardNumber)
            		.customerId(customer.customerId)
                    	.department(department)
                    	.firstName(customer.firstName)
                    	.lastName(customer.lastName)
                    	.itemPurchased(itemPurchased)
                    	.quantity(quantity)
                    	.price(price)
                    	.purchaseDate(purchaseDate)
                    	.zipCode(randomZip)
                    	.city(wdcMap.get(randomZip).getCity())
                    	.country(country)
                    	.state(wdcMap.get(randomZip).getState())
                    	.region(region)
                    	.distrCenterId(wdcMap.get(randomZip).getDistrCenterId())                           
                    	.orderId(orderId)
                    	.productId(productId)
                        .rating(purchasedProductRating)
                    	.category(category)
                    	.deliveryDate(deliveryDate)
                    	.shippingClass(shippingClass)
                    	.segment(segment)
                    	.discount(discount)
                    	.shippingCost(shippingCost)
                    	.sales(sales)
                    	.profit(profit)
                    	.orderPriority(orderPriority)
						.purchaseStatus(purchaseStatusLabel)
						.returnedPurchase(returnedPurchaseLabel)
						.purchaseDeliveryDelayed(purchaseDeliveryDelayedLabel)
						.purchasedProductReviewID(purchasedProductReviewID)
						.friends(friends)
						.sharedWithFriends(sharedWithFriends)
                    	.build();

		
            if (purchase.getDepartment().toLowerCase().contains("not applicable")) {
                Purchase cafePurchase = generateCafePurchase(purchase, faker);
                purchases.add(cafePurchase);
            }
            purchases.add(purchase);
			purchasesGenerated += 1;
        }

        return purchases;

    }



    public static List<BeerPurchase> generateBeerPurchases(int number) {
        List<BeerPurchase> beerPurchases = new ArrayList<>(number);
        Faker faker = new Faker();
        for (int i = 0; i < number; i++) {
            Currency currency = Currency.values()[faker.number().numberBetween(1,4)];
            String beerType = faker.beer().name();
            int cases = faker.number().numberBetween(1,15);
            double totalSale = faker.number().randomDouble(3,12, 200);
            String pattern = "###.##";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            double formattedSale = Double.parseDouble(decimalFormat.format(totalSale));
            beerPurchases.add(BeerPurchase.newBuilder().beerType(beerType).currency(currency).numberCases(cases).totalSale(formattedSale).build());
        }
        return beerPurchases;
    }

    public static StockTransaction generateStockTransaction() {
         List<Customer> customers = generateCustomers(1);
         List<PublicTradedCompany> companies = generatePublicTradedCompanies(1);
         return generateStockTransactions(customers, companies, 1).get(0);
    }

    public static List<StockTransaction> generateStockTransactions(List<Customer> customers, List<PublicTradedCompany> companies, int number) {
        List<StockTransaction> transactions = new ArrayList<>(number);
        Faker faker = new Faker();
        for (int i = 0; i < number; i++) {
            int numberShares = faker.number().numberBetween(100, 50000);
            Customer customer = customers.get(faker.number().numberBetween(0, customers.size()));
            PublicTradedCompany company = companies.get(faker.number().numberBetween(0, companies.size()));
            Date transactionDate = timestampGenerator.get();
            StockTransaction transaction = StockTransaction.newBuilder().withCustomerId(customer.customerId).withTransactionTimestamp(transactionDate)
                    .withIndustry(company.getIndustry()).withSector(company.getSector()).withSharePrice(company.updateStockPrice()).withShares(numberShares)
                    .withSymbol(company.getSymbol()).withPurchase(true).build();
            transactions.add(transaction);
        }
        return transactions;
    }

    public static List<StockTransaction> generateStockTransactionsForIQ(int number) {
        return generateStockTransactions(generateCustomersForInteractiveQueries(), generatePublicTradedCompaniesForInteractiveQueries(), number);
    }


    public static List<PublicTradedCompany> stockTicker(int numberCompanies) {
        return generatePublicTradedCompanies(numberCompanies);
    }


    /**
     * This is a special method for returning the List of PublicTradedCompany to use for
     * Interactive Query examples.  This method uses a fixed list of company names and ticker symbols
     * to make querying by key easier for demo purposes.
     *
     * @return List of PublicTradedCompany for interactive queries
     */
    public static List<PublicTradedCompany> generatePublicTradedCompaniesForInteractiveQueries() {
        List<String> symbols = Arrays.asList("AEBB", "VABC", "ALBC", "EABC", "BWBC", "BNBC", "MASH", "BARX", "WNBC", "WKRP");
        List<String> companyName = Arrays.asList("Acme Builders", "Vector Abbot Corp","Albatros Enterprise", "Enterprise Atlantic",
                "Bell Weather Boilers","Broadcast Networking","Mobile Surgical", "Barometer Express", "Washington National Business Corp","Cincinnati Radio Corp.");
        List<PublicTradedCompany> companies = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < symbols.size(); i++) {
            double volatility = Double.parseDouble(faker.options().option("0.01", "0.02", "0.03", "0.04", "0.05", "0.06", "0.07", "0.08", "0.09"));
            double lastSold = faker.number().randomDouble(2, 15, 150);
            String sector = faker.options().option("Energy", "Finance", "Technology", "Transportation", "Health Care");
            String industry = faker.options().option("Oil & Gas Production", "Coal Mining", "Commercial Banks", "Finance/Investors Services", "Computer Communications Equipment", "Software Consulting", "Aerospace", "Railroads", "Major Pharmaceuticals");
            companies.add(new PublicTradedCompany(volatility, lastSold, symbols.get(i), companyName.get(i), sector, industry));
        }
       return companies;
    }

    /**
     * Special method for generating customers with static customer ID's so
     * we can easily run interactive queries with a predictable list of names
     * @return customer list
     */
    public static List<Customer> generateCustomersForInteractiveQueries() {
        List<Customer> customers = new ArrayList<>(10);
        List<String> customerIds = Arrays.asList("12345678", "222333444", "33311111", "55556666", "4488990011", "77777799", "111188886","98765432", "665552228", "660309116");
        Faker faker = new Faker();
        List<String> creditCards = generateCreditCardNumbers(10);
        for (int i = 0; i < 10; i++) {
            Name name = faker.name();
            String creditCard = creditCards.get(i);
            String customerId = customerIds.get(i);
            customers.add(new Customer(name.firstName(), name.lastName(), customerId, creditCard));
        }
        return customers;
    }



    public static List<PublicTradedCompany> generatePublicTradedCompanies(int numberCompanies) {
        List<PublicTradedCompany> companies = new ArrayList<>();
        Faker faker = new Faker();
        Random random = new Random();
        for (int i = 0; i < numberCompanies; i++) {
            String name = faker.company().name();
            String stripped = name.replaceAll("[^A-Za-z]", "");
            int start = random.nextInt(stripped.length() - 4);
            String symbol = stripped.substring(start, start + 4);
            double volatility = Double.parseDouble(faker.options().option("0.01", "0.02", "0.03", "0.04", "0.05", "0.06", "0.07", "0.08", "0.09"));
            double lastSold = faker.number().randomDouble(2, 15, 150);
            String sector = faker.options().option("Energy", "Finance", "Technology", "Transportation", "Health Care");
            String industry = faker.options().option("Oil & Gas Production", "Coal Mining", "Commercial Banks", "Finance/Investors Services", "Computer Communications Equipment", "Software Consulting", "Aerospace", "Railroads", "Major Pharmaceuticals");
            companies.add(new PublicTradedCompany(volatility, lastSold, symbol, name, sector, industry));
        }

        return companies;

    }


    private static Purchase generateCafePurchase(Purchase purchase, Faker faker) {
        Date date = purchase.getPurchaseDate();
        Instant adjusted = date.toInstant().minus(faker.number().numberBetween(5, 18), ChronoUnit.MINUTES);
        Date cafeDate = Date.from(adjusted);

        return Purchase.builder(purchase)
		.department(faker.options().option("Office Supplies", "Electronics", "Furniture", "Grocery"))
                .itemPurchased(faker.options().option("Shelf", "Desk", "Pen", "Container", "Art", "Labels", "Copiers", "Binders", "Tags"))
                .price(Double.parseDouble(faker.commerce().price(300.00, 705.00)))
		.quantity(faker.number().numberBetween(1, 5))
		.purchaseDate(cafeDate).build();

    }

//////////////////////////////////////////////////////////
//////////// 			For OnMart App       ///////////// 

    public static List<Customer> generateCustomers(int numberCustomers) {
        List<Customer> customers = new ArrayList<>(numberCustomers);
        Faker faker = new Faker();
        List<String> creditCards = generateCreditCardNumbers(numberCustomers);
        for (int i = 0; i < numberCustomers; i++) {
            Name name = faker.name();
            String creditCard = creditCards.get(i);
            String customerId = faker.idNumber().valid();
            customers.add(new Customer(name.firstName(), name.lastName(), customerId, creditCard));
        }
        return customers;
    }
	

//////////////////////////////////////////////////////////
//////////// 			For OnMart App       ///////////// 

    private static List<String> generateCreditCardNumbers(int numberCards) {
        int counter = 0;
        Pattern visaMasterCardAmex = Pattern.compile("(\\d{4}-){3}\\d{4}");
        List<String> creditCardNumbers = new ArrayList<>(numberCards);
        Finance finance = new Faker().finance();
        while (counter < numberCards) {
            String cardNumber = finance.creditCard();
            if (visaMasterCardAmex.matcher(cardNumber).matches()) {
                creditCardNumbers.add(cardNumber);
                counter++;
            }
        }
        return creditCardNumbers;
    }




//////////////////////////////////////////////////////////
//////////// 			For OnMart App       /////////////  


    private static String generateDepartment() {
		Faker faker = new Faker();
        String department = faker.options().option("Office Supplies", "Electronics", "Furniture", "Grocery", "Clothing", "Sports");

        return department;
    }



//////////////////////////////////////////////////////////
//////////// 			For OnMart App       ///////////// 

    private static String generateCategory(String department) {
		Faker faker = new Faker();
		String category;
		
		if(department == "Office Supplies"){
				category = faker.options().option("Appliances","Art","Binders");
        } else if(department == "Electronics"){
				category = faker.options().option("Accessories","Copiers","Machines","Phones");
		} else if(department == "Furniture"){
				category = faker.options().option("Bookcases","Chairs","Furnishings","Tables");
		} else if(department == "Clothing"){
				category = faker.options().option("TShirt","Shorts");
		} else if(department == "Sports"){
				category = faker.options().option("Fitness","Athletic Clothing", "Golf", "Hunting & Fishing");
		} else{
				category = faker.options().option("Snaks", "Beans","Pasta", "Nuts");
		}


        return category;
    }




//////////////////////////////////////////////////////////
//////////// 			For OnMart App       ///////////// 

    private static Date getDeliveryDate(String shippingClass) {
    	
    	Date deliveryDate = timestampGenerator.get();
    	
    	switch(shippingClass) {
			case "Next Day" : 
				deliveryDate = new Date(deliveryDate.getTime() + 24*60*60*1000);
				break;
				
			case "Two Day" :
				deliveryDate = new Date(deliveryDate.getTime() + 2*24*60*60*1000);
				break;
					
			case "Standard" :
				deliveryDate = new Date(deliveryDate.getTime() + 5*24*60*60*1000);
				break;
			default: 
				break;
    	
    	}
    	
		return deliveryDate;
	}


//////////////////////////////////////////////////////////
//////////// 			For OnMart App       ///////////// 

	public static class Customer {
        private String firstName;
        private String lastName;
        private String customerId;
        private String creditCardNumber;

        private Customer(String firstName, String lastName, String customerId, String creditCardNumber) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.customerId = customerId;
            this.creditCardNumber = creditCardNumber;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getCustomerId() {
            return customerId;
        }

        public String getCreditCardNumber() {
            return creditCardNumber;
        }
    }


	
//////////////////////////////////////////////////////////
//////////// 			For OnMart App       ///////////// 
	
	public static void createWdcHashmap() 
	{
		
		// Generate HashMap for all Zip Codes along with heir warehouses and Distributions Centers info
		
		try
		{		
			
			BufferedReader br = new BufferedReader(new FileReader("C:/Workbench/OnMartData/Warehouse_Distribution_Centers.csv"));    // hashmap
			String record =  null;


			// Every Zip Code has the following info (8 fields) in the CSv file for the Warehouse Distribution Center Network
			
			// 1. Zip	
			// 2. City	
			// 3. State	
			// 4. Latitude	
			// 5. Longitude	
			// 6. Facility_ID at given Location/Zip	 
			// 7. Distribution Center ID Serving this Zip Code	
			// 8. Warehouse ID supplying this Distribution Center
			
			
			
			while((record=br.readLine())!=null)
			{

				String zipCodeInfoRow[] = record.split(",");
				wdcMap.put(zipCodeInfoRow[0], new WarehouseDistributionCenter(zipCodeInfoRow[0], zipCodeInfoRow[1], 
														zipCodeInfoRow[2], zipCodeInfoRow[3], 
														zipCodeInfoRow[4], zipCodeInfoRow[5], 
														zipCodeInfoRow[6], zipCodeInfoRow[7],
														0, 0, 0));
											
				zipCodesValuesList.add(zipCodeInfoRow[0]) ;

			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		
		
		
		
    }


//////////////////////////////////////////////////////////
//////////// 			For OnMart App       ///////////// 

	public static String  getRandomZipCodeFromSupplyChain()
	{
			// Get a random zipcode from list of zipcodes
			String randomZip = "";
			int randomIndex = new Random().nextInt(zipCodesValuesList.size());
			randomZip = zipCodesValuesList.get(randomIndex);
			return (randomZip);
	}

	
	

}
