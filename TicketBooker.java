package Train;

import java.util.*;
public class TicketBooker
    {
        //63 berths(upper ,lower , middle)  + ( 18 RAC passengers)
        //10 waiting list tickets ->21 L, 21 M, 21U , 18RAC, 10WL
        static int availableLowerBerths = 1;//normally 21
        static int availableMiddleBerths = 1;//normally 21
        static int availableUpperBerths = 1;//normally 21
        static int availableRacTickets = 1;//normally 18
        static int availableWaitingList = 1;//normally 10

        static Queue<Integer> waitingList = new LinkedList<>();//queue of WL passengers
        static Queue<Integer> racList =  new LinkedList<>();//queu of RAC passengers
        static List<Integer> bookedTicketList =  new ArrayList<>();//list of bookedticket passengers

        static List<Integer> lowerBerthsPositions = new ArrayList<>(Arrays.asList(1));//normally 1,2,...21
        static List<Integer> middleBerthsPositions = new ArrayList<>(Arrays.asList(1));//normally 1,2,...21
        static List<Integer> upperBerthsPositions = new ArrayList<>(Arrays.asList(1));//normally 1,2,...21
        static List<Integer> racPositions = new ArrayList<>(Arrays.asList(1));//normally 1,2,...18
        static List<Integer> waitingListPositions = new ArrayList<>(Arrays.asList(1));//normally 1,2,...10

        static Map<Integer, Passenger> passengers = new HashMap<>();//map of passenger ids to passengers

        //book ticket
        public void bookTicket(Passenger p, int berthInfo,String allotedBerth)
        {
            //assign the seat number and type of berth(L,U,M)
            p.number = berthInfo;
            p.alloted = allotedBerth;
            // add passenger to the map
            passengers.put(p.passengerId,p);
            //add passenger id to the list of booked tickets
            bookedTicketList.add(p.passengerId);
            System.out.println("--------------------------Booked Successfully");
        }

        //adding to RAC
        public void addToRAC(Passenger p,int racInfo,String allotedRAC)
        {
            //assign seat number and type(RAC)
            p.number = racInfo;
            p.alloted = allotedRAC;
            // add passenger to the map
            passengers.put(p.passengerId,p);
            //add passenger id to the queue of RAC tickets
            racList.add(p.passengerId);
            //decrease available RAC tickets by 1
            availableRacTickets--;
            //remove the position that was alloted to the passenger
            racPositions.remove(0);

            System.out.println("--------------------------added to RAC Successfully");
        }

        //adding to WL
        public void addToWaitingList(Passenger p,int waitingListInfo,String allotedWL)
        {
            //assign seat number and type(WL)
            p.number = waitingListInfo;
            p.alloted = allotedWL;
            // add passenger to the map
            passengers.put(p.passengerId,p);
            //add passenger id to the queue of WL tickets
            waitingList.add(p.passengerId);
            //decrease available WL tickets by 1
            availableWaitingList--;
            //remove the position that was alloted to the passenger
            waitingListPositions.remove(0);

            System.out.println("-------------------------- added to Waiting List Successfully");
        }

        public void cancelTicket(int id) {
            Passenger p = passengers.get(id);
            passengers.remove(id);
            bookedTicketList.remove(id);
            int positionBooked = p.number;
            System.out.println("Cancelled ");
            if(p.alloted.equals("L"))
            {
                availableLowerBerths++;
                lowerBerthsPositions.add(positionBooked);
            }
            else if(p.alloted.equals("M"))
            {
                availableMiddleBerths++;
                middleBerthsPositions.add(positionBooked);
            }
            else if(p.alloted.equals("U"))
            {
                availableUpperBerths++;
                upperBerthsPositions.add(positionBooked);
            }
            if(racList.size()>0)
            {
                Passenger passangerFromRAC = passengers.get(racList.poll());
                int positionRac = passangerFromRAC.number;
                racPositions.add(positionRac);
                racList.remove(passangerFromRAC.passengerId);
                availableRacTickets++;
                if(waitingList.size()>0)
                {
                    Passenger passengerFromWaitingList = passengers.get(waitingList.poll());
                    int positionWL = passengerFromWaitingList.number;
                    waitingListPositions.add(positionWL);
                    waitingList.remove(passengerFromWaitingList.passengerId);

                    passengerFromWaitingList.number = racPositions.get(0);
                    passengerFromWaitingList.alloted = "RAC";
                    racPositions.remove(0);
                    racList.add(passengerFromWaitingList.passengerId);
                    availableRacTickets--;
                    availableWaitingList++;
                }
                Main.bookTicket(passangerFromRAC);
            }

        }
        public void printAvailable()
        {
            System.out.println("Available Lower Berths "  + availableLowerBerths);
            System.out.println("Available Middle Berths "  + availableMiddleBerths);
            System.out.println("Available Upper Berths "  + availableUpperBerths);
            System.out.println("Availabel RACs " + availableRacTickets);
            System.out.println("Available Waiting List " + availableWaitingList);
            System.out.println("--------------------------");
        }

        //print all occupied passengers from all types including WL
        public void printPassengers()
        {
            if(passengers.size() == 0)
            {
                System.out.println("No details of passengers");
                return;
            }
            for(Passenger p : passengers.values())
            {
                System.out.println("PASSENGER ID " + p.passengerId );
                System.out.println(" Name " + p.name );
                System.out.println(" Age " + p.age );
                System.out.println(" Status " + p.number + p.alloted);
                System.out.println("--------------------------");
            }
        }

    }
