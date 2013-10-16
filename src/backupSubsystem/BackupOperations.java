package backupSubsystem;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import sessionControl.DerbyUtils;
import sessionControl.Directories;
import sessionControl.Session;

/**
 * This class performs database backup operations for the HMDBS
 * 
 * @author Nicholas Abbey 20522805
 * @version 22/09/13
 */
public class BackupOperations {
	
	public static boolean backup() {
		return backup(Session.currentFocus, false);
	}
	
	/**
	 * Method saves a copy of the currently open cohort data to the 'backups'
	 * directory
	 * 
	 * @param sourceName
	 *            the filename to be copied (without directory)
	 * @param restore
	 *            true only if called from a restore call
	 * @return true if operation successful
	 */
	public static boolean backup(String sourceName, boolean restore) {
		File directory = new File(Directories.dbDir);
		if (directory.exists()) {	// Check db dir actually exists, just in case...
			Session.dbConn.closeConnection();	
			
			String sourcePath = Directories.dbDir + sourceName;

			DateFormat df = new SimpleDateFormat("yyyyMMdd HHmmss");
			Date date = new java.util.Date();
			String reportDate = df.format(date);

			String destZip = Directories.backupDir + sourceName + " " + reportDate;
			if (restore) {
				destZip += " (depr)";
			}
			destZip += ".zip";

			File sourceDir = new File(sourcePath);
			ZipUtility.zipDirectory(sourceDir, destZip);
			
			DerbyUtils.dbConnect(sourceName);
			
			return true; 
		}
		else {
			System.err.println("ERROR 21: Could not find database folder " + directory.getPath()); 
			return false;	
		}
	}

	/**
	 * Method restores database from a backup copy
	 * 
	 * @param backupName
	 *            the record to be instantiated
	 * @return true if operation succeeds
	 */
	public static boolean restore(String backupName) {
		if (!(backupName.startsWith(Session.currentFocus) || Session.currentFocus
				.equals(""))) {
			System.err.println("WARNING: Cannot overwrite a data");
			return false;
		} 

		String cohort = Session.currentFocus;
		// Step 1: backup current state (if one is loaded)
		if (!cohort.equals("")) {
			if (!backup(cohort, true)) {
				System.err.println("ERROR: Could not backup the current database " + Session.currentFocus + ", refer to manual.");
				return false;
			}
		} else {
			cohort = backupName.substring(0, 5);
		}

		String dir = Directories.dbDir + cohort;
		File directory = new File(Directories.dbDir + cohort);

		if (Session.dbConn.isConnected())
			Session.dbConn.closeConnection();

		// Step 2: delete current state (if one is loaded)
		if (directory.exists()) {
			try {
				DeleteUtility.delete(directory);
			} catch (IOException e) {
				System.err.println("ERROR 21: Could not free up current database folder " + dir);
				return false;
			}
		}

		// Step 3: restore backup
		ZipUtility.unZipIt(Directories.backupDir + backupName,
				Directories.dbDir + cohort);

		DerbyUtils.dbConnect(cohort);

		return true;

	}
}
