/****************************************************************************     
 *               BIRLA INSTITUTE OF TECHNOLOGY AND SCIENCE, PILANI
 * 
 *         CS F213 - OBJECT ORIENTED PROGRAMMING (First Semester 2021-22)
 * 
 * ONLINE LAB TEST (Date and Time: 13th November 2021, 9:30 AM to 10:30 AM)
 * 
 * Student's Details (To be duly filled by the student before submitting)
 * ------------------------------------------------------------------------
 * |  Name:    SOLUTION                                                   |
 * |  BITS ID:                                                            |
 * |  Lab Section:                                                        |
 * |  Lab Instructor:                                                     |
 * |                                                                      |
 * |  IPC Room Number:                                                    |
 * |  System Number:                                                      |    
 * |  HackerRank Email:                                                   |
 * ------------------------------------------------------------------------
 * 
 **************************************************************************** */



/* ============ DO NOT MODIFY THE CONTENTS BELOW THIS LINE ============= */

import java.io.*;
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/* ============ DO NOT MODIFY THE CONTENTS ABOVE THIS LINE ============= */


/*********************************** Exceptions ************************************/
class AuthorizationException extends Exception {
    public AuthorizationException(String empID, String resourceID) {
        super("ALERT: Employee " + empID + " is not authorized to access resource " + resourceID);
    }
}

class PullRequestDoesNotExistException extends Exception {
    public PullRequestDoesNotExistException(String commitHash, String projectID) {
        super("INVALID: Pull request with commit " + commitHash + "does not exist in project " + projectID);
    }
}

class UnmergedPullRequestsException extends Exception {
    public UnmergedPullRequestsException(String projectID) {
        super("INVALID: Unmerged pull requests exist in project " + projectID);
    }
}



/*********************************** Interfaces ************************************/
interface Authorizable {
    abstract public void authorizeManager(Manager mg) throws AuthorizationException;
}


/*********************************** Classes ************************************/

/*
 It is advised that you implement the classes in the order given to optimize your
 score and time.
*/
class Team {
    private String teamID;
    private String name;
    private String specialization;
    private ArrayList<Project> projects;
    private ArrayList<Employee> teammates;

    private int totalContributionPoints;

    public Team(String teamID, String name, String specialization) {
        this.teamID = teamID;
        this.name = name;
        this.specialization = specialization;
        this.totalContributionPoints = 0;

        this.projects = new ArrayList<Project>();
        this.teammates = new ArrayList<Employee>();
    }

    public String getTeamID() {
        return this.teamID;
    }

    public void registerNewProject(Project pr) {
        /* add a project to the team's list of projects */
        this.projects.add(pr);
    }

    public void addTeammate(Employee emp) {
        this.teammates.add(emp);
    }

    public void updateContributionPoints(int point) {
        /*  Write method definition to update the contribution points of the
            team. Use the below formula.
            
            Contribution points of the team = (Total contribution points gained through                                                 each commit in each project that the team                                                has handled) / (number of teammates) 
         */

        this.totalContributionPoints = (this.totalContributionPoints + point);
    }

    public void describe() {
        System.out.println("Team Name: " + this.name);
        System.out.println("No. of teammates: " + this.teammates.size());
        System.out.println("Specialization: " + this.specialization);
        if (this.teammates.size() > 0) {
            System.out.println("Contribution Points: " + this.totalContributionPoints / this.teammates.size() * this.projects.size());
        } else {
            System.out.println("Contribution Points: 0");
        }

    }

}


class Employee {
    final static int CURRENT_FINANCIAL_YEAR = 2021;

    private String empID;
    private String name;
    private String dateOfJoining;
    private Team team;

    public Employee(String empID, String name, String dateOfJoining) {
        /* write constructor definition */
        this.empID = empID;
        this.name = name;
        this.dateOfJoining = dateOfJoining;
    }

    public Employee emp() {
        return this;
    }

    public String getEmpID() {
        return this.empID;
    }

    public String getName() {
        return this.name;
    }

    public String getDateOfJoining() {
        return this.dateOfJoining;
    }

    private void setTeam(Team tm) {
        this.team = tm;
        this.team.addTeammate(this);
    }

    public Team getTeam() {
        return this.team;
    }

    public void assignTeam(Team tm) {
        this.setTeam(tm);
    }

    public int numberOfYearsEmployed() {
        /* 
            Write method definition to find the number of years that the 
            employee has been employed.
        */
        int joiningYear = Integer.parseInt(this.dateOfJoining.split("-")[2]);
        return (Employee.CURRENT_FINANCIAL_YEAR - joiningYear);
    }

    public void describe() {
        System.out.println("Employee ID: " + this.empID);
		System.out.println("Name: " + this.name);
		System.out.println("Number of Years Employed: " + this.numberOfYearsEmployed());
    }

}


class Manager extends Employee {

    public Manager(String empID, String name, String dateOfJoining, Team team) {
        /* 
         * Register a new employee who joins as manager. This employee should
         * become the manager of the given team.
         */
        super(empID, name, dateOfJoining);
        super.assignTeam(team);
    }

    public Manager(String empID, String name, String dateOfJoining, String teamID, String teamName,
            String specialization) {
        /* 
         * Register a new employee who joins as manager. This employee should
         * become the manager of a new team, details of which are given.
         */
        super(empID, name, dateOfJoining);
        Team tm = new Team(teamID, teamName, specialization);
        super.assignTeam(tm);
    }

    public void rejectCommit(Commit com) {
        try {
            com.authorizeManager(this);
            com.updateStatus("REJECTED");
            com.project.deletePullRequest(com);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void mergeCommit(Commit com) {
        try {
            com.authorizeManager(this);
            com.updateStatus("MERGED");
            com.project.mergePullRequest(com);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void closeProject(Project pr) {
        try {
            pr.authorizeManager(this);
            pr.closeProject();
        } catch (Exception e) {
            if (e.getMessage() != null) {
                System.out.println(e.getMessage());
            }
        }
    }
}


class SDE extends Employee {
    public HashMap<String, String> skills;

    int contributionPoints;

    public SDE(String empID, String name, String dateOfJoining, Team team, String statementOfSkills) {
        /* 
         * Register a new employee who joins as manager. This employee should
         * become the manager of the given team.
         */
        super(empID, name, dateOfJoining);
        super.assignTeam(team);
        this.contributionPoints = 0;

        this.skills = new HashMap<String, String>();
        String[] skillsArray = statementOfSkills.split(";");
        for (int i = 0; i < skillsArray.length; i++) {
            String[] desc = skillsArray[i].split(":");

            this.skills.put(desc[0], desc[1]);
        }
    }

    public Commit makeCommit(Project pr, String code, String commitDate, String commitTime, String commitMessage) {
        /* write method definition */
        Commit com = new Commit(this, pr, code, commitDate, commitTime, commitMessage);
        return com;
    }

    public void updateContributionPoints(int point) {
        this.contributionPoints += point;
    }

    @Override
    public void describe() {
        super.describe();
        System.out.println("Skills:");
        for (Map.Entry<String, String> entry : this.skills.entrySet()) {
            System.out.println(entry.getKey() + " with " + entry.getValue() + " proficiency");
        }
        System.out.println("Team: " + this.getTeam().getTeamID());
        System.out.println("Contribution Points: " + this.contributionPoints);
    }
}


class Project implements Authorizable {
    public String projectID;
    public Team team;
    public String name;
    public boolean closed;
    
    private ArrayList<Commit> pullRequests;
    private ArrayList<Commit> mergedCommits;

    public Project(String projectID, Team team, String projectName){
        this.projectID = projectID;
        this.team = team;
        this.name = projectName;
        this.closed = false;
        
        this.pullRequests = new ArrayList<Commit>();
        this.mergedCommits = new ArrayList<Commit>();
        
        this.team.registerNewProject(this);
    }

    public String getProjectCode(){
        String code = "";
        for(int i = 0; i < this.mergedCommits.size(); i++) {
            Commit com = this.mergedCommits.get(i);
            code = code + com.code + "\n";
        }
        return code;
    }
    
    public void submitPullRequest(Commit com) {
        this.pullRequests.add(com);
    }
    
    public void deletePullRequest(Commit com) throws PullRequestDoesNotExistException {
        if (this.pullRequests.contains(com)){
            this.pullRequests.remove(com);        
        } else {
            throw new PullRequestDoesNotExistException(com.commitHash, this.projectID);
        }
    }
    
    public void mergePullRequest(Commit com) throws PullRequestDoesNotExistException {
        if (this.pullRequests.contains(com)){
            this.pullRequests.remove(com);
            this.mergedCommits.add(com);
        } else {
            throw new PullRequestDoesNotExistException(com.commitHash, this.projectID);
        }
    }
    
    public void closeProject() throws UnmergedPullRequestsException {
        /* 
         * In order to successfully close a project, it is required that all
         * commits made towards the project are either MERGED or REJECTED. There should 
         * be no commits having the status of SUBMITTED or APPROVED. This method 
         * should return the status of the closing of the project (true if successfully
         * closed, false otherwise.) 
         */
        if (this.pullRequests.size() > 0) {
            throw new UnmergedPullRequestsException(this.projectID);
        } else {
            this.closed = true;
        }
    }
    
    public void authorizeManager(Manager mg) throws AuthorizationException {
        if (!this.team.getTeamID().equals(mg.getTeam().getTeamID())) {
            throw new AuthorizationException(mg.getEmpID(), this.projectID);
        }
    }
}

class Commit implements Authorizable {
    Project project;
    String code;
    String commitDate;
    String commitTime;
    String commitMessage;
    SDE developer;
    String commitHash;

    String status; // one out of SUBMITTED, MERGED or REJECTED

    /*
        the following variable `contributionPointsSystem` maps the commit's
        status to the points that are awarded to the developer for the 
        contribution.
        
        follow the below scheme for setting the points:
            - SUBMITTED: 0
            - MERGED: 3
            - REJECTED: -4
    */
    HashMap<String, Integer> contributionPointsSystem;

    Manager approvingManager;

    public Commit(SDE dev, Project pr, String code, String commitDate, String commitTime, String commitMessage) {
        /*  write constructor definition */
        this.developer = dev;
        this.project = pr;
        this.code = code;
        this.commitDate = commitDate;
        this.commitTime = commitTime;
        this.commitMessage = commitMessage;
        this.status = "SUBMITTED";
        this.approvingManager = null;
        this.hashCommit();
        System.out.println(this.commitHash);

        this.contributionPointsSystem = new HashMap<String, Integer>();
        this.contributionPointsSystem.put("SUBMITTED", 0);
        this.contributionPointsSystem.put("MERGED", 3);
        this.contributionPointsSystem.put("REJECTED", -4);

        this.updateStatus("SUBMITTED");
        this.project.submitPullRequest(this);
    }

    public void updateStatus(String status) {
        this.status = status;
        int point = this.contributionPointsSystem.get(this.status);
        this.developer.updateContributionPoints(point);
        this.project.team.updateContributionPoints(point);
    }

    public void describe() {
        System.out.println("Commit Hash: " + this.commitHash);
        System.out.println(
                "Submitted at " + this.commitTime + " on " + this.commitDate + " by " + this.developer.getEmpID());
        if (this.status == "MERGED") {
            System.out.println("Status: " + this.status + " by " + this.approvingManager.getEmpID());
        } else {
            System.out.println("Status: " + this.status);
        }
    }

    public void hashCommit() {
        String commitMetaString = this.code + this.commitDate + this.commitTime + this.commitMessage;

        try {
            MessageDigest digester = MessageDigest.getInstance("SHA-256");
            digester.update(commitMetaString.getBytes(StandardCharsets.UTF_8));
            byte[] digest = digester.digest();

            String hexdigest = String.format("%064x", new BigInteger(1, digest));
            this.commitHash = hexdigest;
        } catch (NoSuchAlgorithmException e) {
            if (e.getMessage() != null) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void authorizeManager(Manager mg) throws AuthorizationException {
        if (!this.developer.getTeam().getTeamID().equals(mg.getTeam().getTeamID())) {
            throw new AuthorizationException(mg.getEmpID(), this.commitHash);
        }
        this.approvingManager = mg;
    }
}



/* ============ DO NOT MODIFY THE CONTENTS BELOW THIS LINE ============= */
class Factory {
    private ArrayList<Employee> employees = new ArrayList<Employee>();
    private ArrayList<Manager> managers = new ArrayList<Manager>();
    private ArrayList<SDE> sdes = new ArrayList<SDE>();
    private ArrayList<Team> teams = new ArrayList<Team>();
    private ArrayList<Project> projects = new ArrayList<Project>();
    private ArrayList<Commit> commits = new ArrayList<Commit>();
    
    public Scanner sc = new Scanner(System.in);
    
    public void handle0000() {
        String line1 = this.sc.nextLine();
        
        Commit com = this.getCommit(line1);
        com.describe();
    }
    
    public void handle0001() {
        String line1 = this.sc.nextLine();
        String line2 = this.sc.nextLine();
        String line3 = this.sc.nextLine();
        String line4 = this.sc.nextLine();
        String line5 = this.sc.nextLine();

        Team tm = this.getTeam(line4);
        SDE dev = new SDE(line1, line2, line3, tm, line5);
        this.sdes.add(dev);
        this.employees.add(dev.emp());
    }
    
    public void handle0010() {
        String line1 = this.sc.nextLine();
        
        Team tm = this.getTeam(line1);
        tm.describe();
    }
    
    public void handle0011() {
        /* empty */
    }
    
    public void handle0100() {
        String line1 = this.sc.nextLine();
        String line2 = this.sc.nextLine();
        String line3 = this.sc.nextLine();
        String line4 = this.sc.nextLine();
        
        Team tm = this.getTeam(line4);
        Manager mg = new Manager(line1, line2, line3, tm);
        this.managers.add(mg);
        this.employees.add(mg.emp());
    }
    
    public void handle0101() {
        String line1 = this.sc.nextLine();
        String line2 = this.sc.nextLine();
        String line3 = this.sc.nextLine();
        String line4 = this.sc.nextLine();
        String line5 = this.sc.nextLine();
        String line6 = this.sc.nextLine();
        
        Manager mg = new Manager(line1, line2, line3, line4, line5, line6);
        this.managers.add(mg);
        this.employees.add(mg.emp());
        this.teams.add(mg.getTeam());
    }
    
    public void handle0110() {
        /* empty */
    }
    
    public void handle0111() {
        String line1 = this.sc.nextLine();
        String line2 = this.sc.nextLine();
        String line3 = this.sc.nextLine();
        
        Team tm = new Team(line1, line2, line3);
        this.teams.add(tm);
    }
    
    public void handle1000() {
        String line1 = this.sc.nextLine();
        String line2 = this.sc.nextLine();
        
        Employee emp = this.getEmployee(line1);
        Team tm = this.getTeam(line2);
        emp.assignTeam(tm);
    }
    
    public void handle1001() {
        String line1 = this.sc.nextLine();
        String line2 = this.sc.nextLine();
        String line3 = this.sc.nextLine();
        
        Team tm = this.getTeam(line2);
        Project pr = new Project(line1, tm, line3);
        this.projects.add(pr);
    }
    
    public void handle1010() {
        String line1 = this.sc.nextLine();
        String line2 = this.sc.nextLine();
        String line3 = this.sc.nextLine();
        String line4 = this.sc.nextLine();
        String line5 = this.sc.nextLine();
        String line6 = this.sc.nextLine();
        
        SDE dev = this.getSDE(line1);
        Project pr = this.getProject(line2);
        Commit com = dev.makeCommit(pr, line3, line4, line5, line6);
        this.commits.add(com);
    }
    
    public void handle1011() {
        String line1 = this.sc.nextLine();
        String line2 = this.sc.nextLine();
        
        Manager mg = this.getManager(line1);
        Commit com = this.getCommit(line2);
        mg.rejectCommit(com);
    }
    
    public void handle1100() {
        String line1 = this.sc.nextLine();
        String line2 = this.sc.nextLine();
        
        Manager mg = this.getManager(line1);
        Commit com = this.getCommit(line2);
        mg.mergeCommit(com);
    }
    
    public void handle1101() {
        String line1 = this.sc.nextLine();
        String line2 = this.sc.nextLine();
        
        Manager mg = this.getManager(line1);
        Project pr = this.getProject(line2);
        mg.closeProject(pr);
    }
    
    public void handle1110() {
        String line1 = this.sc.nextLine();
        
        Project pr = this.getProject(line1);
        System.out.println(pr.getProjectCode());
    }
    
    public void handle1111() {
        String line1 = this.sc.nextLine();
        
        SDE dev = this.getSDE(line1);
        dev.describe();
    }
    
    
    private Employee getEmployee(String empID) {
        for(int i = 0; i < this.employees.size(); i++) {
            Employee emp = this.employees.get(i);
            if (emp.getEmpID().equals(empID)) {
                return emp;
            }
        }
        return null;
    }
    
    private Manager getManager(String empID) {
        for(int i = 0; i < this.managers.size(); i++) {
            Manager mg = this.managers.get(i);
            if (mg.getEmpID().equals(empID)) {
                return mg;
            }
        }
        return null;
    }
    
    private SDE getSDE(String empID) {
        for(int i = 0; i < this.sdes.size(); i++) {
            SDE dev = this.sdes.get(i);
            if (dev.getEmpID().equals(empID)) {
                return dev;
            }
        }
        return null;
    }
    
    private Team getTeam(String teamID) {
        for(int i = 0; i < this.teams.size(); i++) {
            Team tm = this.teams.get(i);
            if (tm.getTeamID().equals(teamID)) {
                return tm;
            }
        }
        return null;
    }
    
    private Project getProject(String projectID) {
        for(int i = 0; i < this.projects.size(); i++) {
            Project pr = this.projects.get(i);
            if (pr.projectID.equals(projectID)) {
                return pr;
            }
        }
        return null;
    }
    
    private Commit getCommit(String commitHash) {
        for(int i = 0; i < this.commits.size(); i++) {
            Commit com = this.commits.get(i);
            if (com.commitHash.equals(commitHash)) {
                return com;
            }
        }
        return null;
    }
}

class Solution {
    public static void main(String[] args) {
        Factory fc = new Factory();
        
        int noOfCommands = Integer.parseInt(fc.sc.nextLine());
        for(int i = 0; i < noOfCommands; i++) {
            int command = Integer.parseInt(fc.sc.nextLine());
            //System.out.println(command);
            
            if (command == 0){
                fc.handle0000();                    
            }
            if (command == 1){
                fc.handle0001();                    
            }
            if (command == 10){
                fc.handle0010();                    
            }
            if (command == 11){
                fc.handle0011();                    
            }
            if (command == 100){
                fc.handle0100();                    
            }
            if (command == 101){
                fc.handle0101();                    
            }
            if (command == 110){
                fc.handle0110();                    
            }
            if (command == 111){
                fc.handle0111();                    
            }
            if (command == 1000){
                fc.handle1000();                    
            }
            if (command == 1001){
                fc.handle1001();                    
            }
            if (command == 1010){
                fc.handle1010();                    
            }
            if (command == 1011){
                fc.handle1011();                    
            }
            if (command == 1100){
                fc.handle1100();                    
            }
            if (command == 1101){
                fc.handle1101();                    
            }
            if (command == 1110){
                fc.handle1110();                    
            }
            if (command == 1111){
                fc.handle1111();                    
            }
        }
    }
}
/* ============ DO NOT MODIFY THE CONTENTS ABOVE THIS LINE ============= */
