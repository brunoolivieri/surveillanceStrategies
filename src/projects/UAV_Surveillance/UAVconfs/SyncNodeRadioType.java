package projects.UAV_Surveillance.UAVconfs;

public class SyncNodeRadioType {

	public int radioRange = 0; 		 // Meters
	public double delay = 0;   		 // ms
	public int energyPerPackageTX = 0; // mA
	public int energyPerPackageRX = 0; // mA
	public int dataRate = 0; 		 //		kbits/second
	public double packageColisionTax = 0; 
	
	public SyncNodeRadioType(String radioType){
	
		 switch (radioType) {
         case "high802":  

        	 
                  break;
         case "medium802":  
        	 
        	      break;
         
         default: // "short802"

        	 
                  break;
		 }

	
	}
	

	
}
