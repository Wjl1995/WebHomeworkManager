package pers.derbyDao;

public class derbyDaoCE {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		StudentDao studentDao = StudentDao.getInstance();
		
		studentDao.printAllTables();
	}

}
