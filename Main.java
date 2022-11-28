package Train;


import java.util.Scanner;



public class Main
    {
        //function for bokoing ticket
        public static void bookTicket(Passenger p)
        {
            TicketBooker booker = new TicketBooker();
            if(TicketBooker.availableWaitingList == 0)
            {
                System.out.println("No Tickets Available");
                return;
            }
            //check if preferred berth is available
            if((p.berthPreference.equals("L") && TicketBooker.availableLowerBerths > 0 )||
                    (p.berthPreference.equals("M") && TicketBooker.availableMiddleBerths > 0) ||
                    (p.berthPreference.equals("U") && TicketBooker.availableUpperBerths > 0))
            {
                System.out.println("Preferred Berth Available");
                switch (p.berthPreference) {
                    case "L":
                        System.out.println("Lower Berth Given");
                        //call booking function in the TicketBooker class
                        booker.bookTicket(p, (TicketBooker.lowerBerthsPositions.get(0)), "L");
                        //remove the booked position from available positions and also decrease available seats of that
                        // particular type
                        TicketBooker.lowerBerthsPositions.remove(0);
                        TicketBooker.availableLowerBerths--;


                        break;
                    case "M":
                        System.out.println("Middle Berth Given");
                        //call booking function in the TicketBooker class
                        booker.bookTicket(p, (TicketBooker.middleBerthsPositions.get(0)), "M");
                        //remove the booked position from available positions and also decrease available seats of that
                        // particular type
                        TicketBooker.middleBerthsPositions.remove(0);
                        TicketBooker.availableMiddleBerths--;

                        break;
                    case "U":
                        System.out.println("Upper Berth Given");
                        //call booking function in the TicketBooker class
                        booker.bookTicket(p, (TicketBooker.upperBerthsPositions.get(0)), "U");
                        //remove the booked position from available positions and also decrease available seats of that
                        // particular type
                        TicketBooker.upperBerthsPositions.remove(0);
                        TicketBooker.availableUpperBerths--;
                        break;
                }

            }
            //preference not available -> book the available berth

            else if(TicketBooker.availableLowerBerths > 0)
            {
                System.out.println("Lower Berth Given");
                //call booking function in the TicketBooker class
                booker.bookTicket(p,(TicketBooker.lowerBerthsPositions.get(0)),"L");
                //remove the booked position from available positions and also decrease available seats of that
                // particular type
                TicketBooker.lowerBerthsPositions.remove(0);
                TicketBooker.availableLowerBerths--;


            }
            else if(TicketBooker.availableMiddleBerths > 0)
            {
                System.out.println("Middle Berth Given");
                //call booking function in the TicketBooker class
                booker.bookTicket(p,(TicketBooker.middleBerthsPositions.get(0)),"M");
                //remove the booked position from available positions and also decrease available seats of that
                // particular type
                TicketBooker.middleBerthsPositions.remove(0);
                TicketBooker.availableMiddleBerths--;

            }
            else if(TicketBooker.availableUpperBerths > 0)
            {
                System.out.println("Upper Berth Given");
                //call booking function in the TicketBooker class
                booker.bookTicket(p,(TicketBooker.upperBerthsPositions.get(0)),"U");
                //remove the booked position from available positions and also decrease available seats of that
                // particular type
                TicketBooker.upperBerthsPositions.remove(0);
                TicketBooker.availableUpperBerths--;

            }
            // if no berth available go to RAC
            else if(TicketBooker.availableRacTickets > 0)
            {
                System.out.println("RAC available");
                booker.addToRAC(p,(TicketBooker.racPositions.get(0)),"RAC" );
            }
            // if no RAC available go to WL
            else if(TicketBooker.availableWaitingList > 0)
            {
                System.out.println("Added to Waiting List");
                booker.addToWaitingList(p,(TicketBooker.waitingListPositions.get(0)),"WL");

            }

        }
        public static void main(String[] args)
        {
            Scanner s = new Scanner(System.in);
            boolean loop = true;
            //loop to get choices from user until he stops
            while(loop)
            {
                System.out.println(" 1. Book Ticket \n 2. Cancel Ticket \n 3. Available Tickets \n 4. Booked Tickets \n 5. Exit");
                int choice = s.nextInt();
                switch(choice)
                {
                    // book ticket
                    case 1:
                    {
                        //get details from Passenger
                        System.out.println("Enter Passenger name,age and berth preference (L,M or U)");
                        String name = s.next();
                        int age = s.nextInt();
                        //get berth preference (L,U,M)
                        String berthPreference = s.next();
                        //create a passenger object
                        Passenger p = new Passenger(name,age,berthPreference);
                        //booking
                        bookTicket(p);
                    }
                    break;
                    //cancel ticket
                    case 2:
                    {
                        //get passenger id to cancel
                        System.out.println("Enter passenger Id to cancel");
                        int id = s.nextInt();
                        cancelTicket(id);
                    }
                    break;
                    case 3: {
                        TicketBooker booker = new TicketBooker();
                        booker.printAvailable();
                    }
                    case 4: {
                        TicketBooker booker = new TicketBooker();
                        booker.printPassengers();
                    }
                    default:
                        break;
                }
            }
        }


    private static void cancelTicket(int id) {
        TicketBooker booker = new TicketBooker();
        if(!TicketBooker.passengers.containsKey(id))
            System.out.println("Enter Valid id");
        else
            booker.cancelTicket(id);

    }
    }
