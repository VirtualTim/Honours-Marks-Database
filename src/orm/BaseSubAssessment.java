package orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.StatisticsCalculator;
import sessionControl.Session;

public class BaseSubAssessment {

    private int subAssessmentID;
    public StringBuffer name = new StringBuffer (30);
    private Assessment parentAssessment;
    public StringBuffer maxMark = new StringBuffer (6);
    public StringBuffer assessmentPercent = new StringBuffer (6);
    private List<Mark> marks = new ArrayList<Mark>();
    public StringBuffer aveMark = new StringBuffer(6);
    public StringBuffer standDev = new StringBuffer(6); 
    // Maybe add average mark for this subassessment over all marks
    
    /**
     * Constructor run for sub assessments in CohortData.units or CohortData.assessments; contains all
     * information about subassessments, has parent assessment, is non-specific to students
     * 
     * @param subAssessmentID the subassessment ID
     * @param assessment the parent assessment
     */
    public BaseSubAssessment(int subAssessmentID, Assessment assessment) {
    	try (Statement s = Session.dbConn.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet subassessmentRS = s.executeQuery("SELECT * FROM SubAssessment WHERE SubAssessmentID=" + subAssessmentID)) {
            
            // There will only be one subAssessment returned as subAssessmentID is unique
    		// Called with this constructor, no data about marks will exist
    		while (subassessmentRS.next()) {
            
	            setSubAssessmentID(subAssessmentID);
	            
	            setName(subassessmentRS.getString("SubAssessmentName"));
	            setMaxMark(subassessmentRS.getInt("MaxMarks"));
	            setAssessmentPercent(subassessmentRS.getInt("AssessmentPercent"));
	            this.parentAssessment = assessment;
	            
	            
    		}
            
        } catch (SQLException ex) {
            Logger.getLogger(BaseSubAssessment.class.getName()).log(Level.SEVERE, null, ex);
        }
    	
    	try (Statement s = Session.dbConn.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet subassessmentMarksRS = s.executeQuery("SELECT * FROM SubAssessmentMark WHERE SubAssessmentID=" + subAssessmentID)) {
            
            // Will be a list of subAssessments marks returned, as many subAssessments marks can belong to a subassessment
    		// We are adding all of them to the list
            while (subassessmentMarksRS.next()) {
            	Mark nextMark = new Mark(subAssessmentID, subassessmentMarksRS.getInt("StudentID"), subassessmentMarksRS.getInt("MarkerID"), (SubAssessment) this);
	            this.marks.add(nextMark);
            }
            
            setAveMark(StatisticsCalculator.subAssessAve(subAssessmentID));
        } catch (SQLException ex) {
            Logger.getLogger(BaseAssessment.class.getName()).log(Level.SEVERE, null, ex);
        }
    	
    }
    
    /**
     * Constructor run when creating a subAssessment for CohortData.subassessments - single 'master' subassessment object for each subassessment ID,
     * containing all related marks for that subassessment. Does not have a parent assessment.
     * 
     * @param subAssessmentID the ID of the subassessment
     */
    public BaseSubAssessment(int subAssessmentID) {
    	try (Statement s = Session.dbConn.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet subassessmentRS = s.executeQuery("SELECT * FROM SubAssessment WHERE SubAssessmentID=" + subAssessmentID)) {
            
            // There will only be one subAssessment returned as subAssessmentID is unique
    		// Called with this constructor, no data about marks will exist
    		while (subassessmentRS.next()) {
            
	            setSubAssessmentID(subAssessmentID);
	            
	            setName(subassessmentRS.getString("SubAssessmentName"));
	            setMaxMark(subassessmentRS.getInt("MaxMarks"));
	            setAssessmentPercent(subassessmentRS.getInt("AssessmentPercent"));
    		}
            
        } catch (SQLException ex) {
            Logger.getLogger(BaseSubAssessment.class.getName()).log(Level.SEVERE, null, ex);
        }
    	
    	try (Statement s = Session.dbConn.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet subassessmentMarksRS = s.executeQuery("SELECT * FROM SubAssessmentMark WHERE SubAssessmentID=" + subAssessmentID)) {
            
            // Will be a list of subAssessments marks returned, as many subAssessments marks can belong to a subassessment
    		// We are adding all of them to the list
            while (subassessmentMarksRS.next()) {
            	Mark nextMark = new Mark(subAssessmentID, subassessmentMarksRS.getInt("StudentID"), subassessmentMarksRS.getInt("MarkerID"), (SubAssessment) this);
	            this.marks.add(nextMark);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BaseAssessment.class.getName()).log(Level.SEVERE, null, ex);
        }
    	
    	
    }
    
    /**
     * Constructor run when creating a subAssessment related to a specific student
     * 
     * @param subAssessmentID subassessment ID
     * @param studentID student's ID
     * @param assessment parent of the subassessment
     */
    public BaseSubAssessment(int subAssessmentID, int studentID, Assessment assessment) {
    	this.marks = new ArrayList<>();
    	
    	try (Statement s = Session.dbConn.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet subassessmentRS = s.executeQuery("SELECT * FROM SubAssessment WHERE SubAssessmentID=" + subAssessmentID)) {
            
            // There will only be one subAssessment returned as subAssessmentID is unique
    		// Called with this constructor, no data about marks will exist
    		while (subassessmentRS.next()) {
            
	            setSubAssessmentID(subAssessmentID);
	            
	            setName(subassessmentRS.getString("SubAssessmentName"));
	            setMaxMark(subassessmentRS.getInt("MaxMarks"));
	            setAssessmentPercent(subassessmentRS.getInt("AssessmentPercent"));
	            this.parentAssessment = assessment;
    		}
        } catch (SQLException ex) {
            Logger.getLogger(BaseSubAssessment.class.getName()).log(Level.SEVERE, null, ex);
        }
    	
    	try (Statement s = Session.dbConn.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet subassessmentMarksRS = s.executeQuery("SELECT MarkerID FROM SubAssessmentMark WHERE SubAssessmentID=" + subAssessmentID + " AND StudentID =" + studentID)) {
            
            // Will be a list of subAssessments marks returned, as many subAssessments marks can belong to a subassessment
    		// We are adding all of them to the list
            while (subassessmentMarksRS.next()) {
            	Mark nextMark = new Mark(subAssessmentID, studentID, subassessmentMarksRS.getInt("MarkerID"), (SubAssessment)this);
	            this.marks.add(nextMark);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BaseAssessment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public BaseSubAssessment(String name, Assessment parentAssessment, int assessmentPercent, int maxMark) throws SQLException {
        try (Statement s = Session.dbConn.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            s.execute("INSERT INTO SubAssessment(SubAssessmentName, AssessmentID, AssessmentPercent, MaxMarks) VALUES ('"
                    + name + "', "
                    + parentAssessment.getAssessmentID() + ", "
                    + assessmentPercent + ", "
                    + maxMark + ")");
            
            // TODO: Wait if we have autogenerated SubAssessmentID then how do we initialise this?
            setSubAssessmentID(subAssessmentID);
            setName(name);
            setParentAssessment(parentAssessment);
            setAssessmentPercent(assessmentPercent);
            setMaxMark(maxMark);
            setMarks(null);
        } catch (SQLException ex) {
            Logger.getLogger(BaseStudent.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException(ex.getMessage());
        }
    }
    
    public int getSubAssessmentID() {
        return subAssessmentID;
    }
    
    public void setSubAssessmentID(int subAssessmentID) {
        this.subAssessmentID = subAssessmentID;
    }
    
    public StringBuffer getName() {
        return name;
    }
    
    public void setName(String name) {
    	this.name.replace(0, this.name.capacity(), name);
    }
    
    public Assessment getParentAssessment() {
        return parentAssessment;
    }
    
    public void setParentAssessment(Assessment parentAssessment) {
        this.parentAssessment = parentAssessment;
    }
    
    public int getMaxMark() {
    	return Integer.parseInt(maxMark+"");
    }
    
    public void setMaxMark(int maxMark) {
    	this.maxMark.replace(0, this.maxMark.capacity(),  Integer.toString(maxMark));
    }
    
    public double getAveMark() {
    	return Double.parseDouble(aveMark+"");
    }
    
    public void setAveMark(double aveMark) {
    	this.aveMark.replace(0, this.aveMark.capacity(),  Double.toString(aveMark));
    	this.aveMark.setLength(5);
    }
    
    public double getStandDev() {
    	return Double.parseDouble(standDev+"");
    }
    
    public void setStandDev(double standDev) {
    	this.standDev.replace(0, this.standDev.capacity(),  Double.toString(standDev));
    	this.standDev.setLength(5);
    }
    
    
    /**
     * Returns the string buffer displaying the range of scores over the subassessment
     * @param min minimum score in range
     * @param max maximum score in range
     */
    public StringBuffer getRange() {
    	return standDev;
    }
    
    /**
     * Sets the string buffer displaying the range of scores over the subassessment
     * @param min minimum score in range
     * @param max maximum score in range
     */
    public void setRange(double min, double max) {
    	
    	this.standDev.replace(0, this.standDev.capacity(),  Double.toString(min));
    	this.standDev.setLength(5);
    	this.standDev.append(" - ");
    	this.standDev.append(max);
    	this.standDev.setLength(13);
    }
    
    
    public double getAssessmentPercent() {
    	return Integer.parseInt(assessmentPercent+"");
    }
    
    public void setAssessmentPercent (int assessmentPercent) {
    	this.assessmentPercent.replace(0, this.assessmentPercent.capacity(),  Integer.toString(assessmentPercent));
    }
    
    public List<Mark> getMarks() {
        return marks;
    }
    
    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }
}
