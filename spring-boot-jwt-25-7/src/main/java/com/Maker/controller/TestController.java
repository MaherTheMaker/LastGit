package com.Maker.controller;



import com.Maker.dao.ReceiptRepo;
import com.Maker.model.*;
import com.Maker.service.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/Financial")
public class TestController {
    @Autowired
    private MoneySafeService moneySafeService;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private PatientService patientService;
    @Autowired
    private ExpensesService expensesService;


    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TreatmentPlanService treatmentPlanService;


    @Autowired
    private ToothProcedureService toothProcedureService;

    @PostMapping("/AddSafe/{uID}")
    private ResponseEntity<MoneySafe> AddSafe(@PathVariable int uID, @RequestBody MoneySafe moneySafe){
        return ResponseEntity.ok().body(moneySafeService.addMoneySafe(moneySafe,uID));
    }

    @GetMapping("/AllSafes")
    private ResponseEntity<List<MoneySafe>> GetSafes() {
        return ResponseEntity.ok().body(moneySafeService.getAllMoneySafe());
    }

    @GetMapping("/getSafeName/{name}")
    private ResponseEntity<MoneySafe> getSafe(@PathVariable String name){
        System.out.printf("name");
        return ResponseEntity.ok().body(moneySafeService.getMoneySafe(name));
    }

    @GetMapping("/getSafeID/{id}")
    private ResponseEntity<MoneySafe> getSafe(@PathVariable int id){
        return ResponseEntity.ok().body(moneySafeService.getMoneySafe(id));
    }

    @PostMapping("/editSafe/{id}")
    private ResponseEntity<MoneySafe> editProcedure(@PathVariable int id , @RequestBody MoneySafe moneySafe){
        return ResponseEntity.ok().body(moneySafeService.editMoneySafe(id , moneySafe));

    }


    @PostMapping("{pId}/Receipt/add")
    private ResponseEntity<Receipt> addReceipt(@PathVariable int pId,@RequestBody ReceiptForm receipt){


        return ResponseEntity.accepted().body(receiptService.add(pId, receipt));

    }


    @PostMapping("/Receipt/{id}/edit")
    private  ResponseEntity<Receipt> editReceipt(@PathVariable int id, @RequestBody Receipt receipt){
        return ResponseEntity.accepted().body(receiptService.editReceipt(id,receipt));
    }


    @GetMapping("/getPatientToothProcedureUnpaid/{pid}")
    private ResponseEntity<List<ToothProcedure>> getPatientToothProcedure (@PathVariable int pid){
        return ResponseEntity.ok().body(treatmentPlanService.getAllUnpaidTP(pid,false));
    }

    @GetMapping("/Dashboard")
    public ResponseEntity<SafeParam>  getTest(){
        SafeParam safeParam = new SafeParam();
        safeParam.setAllExpenses(expensesService.getTotalExpenses());
        safeParam.setAllRevenue(receiptService.getTotalReceipt());
        safeParam.setAllUnpaid(toothProcedureService.getTotalExpenses());
        return ResponseEntity.ok().body(safeParam);
    }

    @GetMapping("/Receipt/{id}/getReceipt")
    private ResponseEntity<Receipt> getReceipt(@PathVariable int id ){
        return ResponseEntity.ok().body(receiptService.getReceipt(id));
    }

    @GetMapping("/Receipt/getBySafe/{safeName}")
    private ResponseEntity<List<Receipt>> getAllReceiptBySafe(@PathVariable String safeName){
        return ResponseEntity.ok().body(receiptService.getAllReceiptBySafe(safeName));
    }

    @GetMapping("/Receipt/getBySafeNameAndDate/{safeName}")
    private ResponseEntity<List<Receipt>> getAllReceiptBySafeAndByDate(@PathVariable String safeName,@RequestBody date date1){

        return ResponseEntity.ok().body(receiptService.getAllReceiptBySafeAndDate(safeName,date1.date));

    }
    @GetMapping("/Receipt/getByDate")
    private ResponseEntity<List<Receipt>> getAllReceiptByDate(@RequestBody date date1){

        return ResponseEntity.ok().body(receiptService.getAllReceipt(date1.date));

    }
    @GetMapping("/Receipt/getByDateRange")
    private ResponseEntity<List<Receipt>> getAllReceiptByDateRange(@RequestBody rangeDate date){

        System.out.println(date.start+ " "+ date.end);
        return ResponseEntity.accepted().body(receiptService.getAllReceiptRange(date.start,date.end));
    }


    @GetMapping("/Receipt/getAllReceipt")
    private ResponseEntity<List<Receipt>> getAll (){
        return ResponseEntity.ok().body(receiptService.getAllReceipt());
    }


    //Expenses
    @PostMapping("/expenses/add")
    private ResponseEntity<Expenses> addExpense(@RequestBody ExpensesForm expenses){

        return ResponseEntity.accepted().body(expensesService.add(expenses));

    }

    @GetMapping("/expense/{id}/getExpense")
    private ResponseEntity<Expenses> getExpense(@PathVariable int id ){
        return ResponseEntity.ok().body(expensesService.getExpense(id));
    }

    @GetMapping("/expense/getByDate")
    private ResponseEntity<List<Expenses>> getAllExpensesByDate(@RequestBody date date1){


        return ResponseEntity.ok().body(expensesService.getExpensesDate(date1.date));

    }
    @GetMapping("/expense/getByDateRange")
    private ResponseEntity<List<Expenses>> getAllExpensesByDateRange(@RequestBody rangeDate rangeDate){


        return ResponseEntity.ok().body(expensesService.getExpensesDateRange( rangeDate.start,rangeDate.end));

    }

    @GetMapping("/expense/getBySafe/{name}")
    private ResponseEntity<List<Expenses>> getAllExpensesBySafe(@PathVariable String name){


        return ResponseEntity.ok().body(expensesService.getMoneySafeExpenses(name));

    }

    @GetMapping("/expense/getBySafeAndDate")
    private ResponseEntity<List<Expenses>> getMoneySafeExpensesInDate(@RequestBody DateAndSafeName dateAndSafeName){

        return ResponseEntity.ok().body(expensesService.getMoneySafeExpensesInDate(dateAndSafeName.safeName,dateAndSafeName.d));
    }




    //TRANSACTION
    @PostMapping("/transaction/add/{id}")
    private ResponseEntity<Transaction> addTransaction (@RequestBody Transaction transaction , @PathVariable int id){

        return ResponseEntity.accepted().body(transactionService.add(transaction , id));
    }


    @GetMapping("/transaction/{id}/getTransaction")
    private ResponseEntity<Transaction> getTransaction(@PathVariable int id ){
        return ResponseEntity.ok().body(transactionService.getTransaction(id));
    }


    @GetMapping("/transaction/getByDate/")
    private ResponseEntity<List<Transaction>> getAllTransactionByDate(@RequestBody loDate loDate){
        return ResponseEntity.ok().body(transactionService.getAllTransaction(loDate.date));
    }


    @GetMapping("/transaction/getBySafe/{safeName}")
    private ResponseEntity<List<Transaction>> getAllTransactionBySafe(@PathVariable String safeName){

        return ResponseEntity.ok().body(transactionService.getAllSafeTransaction(safeName));
    }



    @GetMapping("/transaction/getBySafeAndDate")
    private ResponseEntity<List<Transaction>> getAllTransactionBySafeAndDate(@RequestBody LocalDateAndSafeName dateAndSafeName){
        return ResponseEntity.ok().body(transactionService.getAllTransactionBySafeAndDate(dateAndSafeName.safeName,dateAndSafeName.d));
    }


    @GetMapping("/PatientsBalance")
    private ResponseEntity<List<PatientFinancialDetails>> getPatientFinancialDetails () {
        return (ResponseEntity<List<PatientFinancialDetails>>) ResponseEntity.ok().body(treatmentPlanService.getPatientFinancialDetails());
    }


    @GetMapping("/{pid}/patientPaidUnPaid")
    private ResponseEntity<PatientPaidUnPaid> PatientPaidUnPaid (@PathVariable int pid){
            PatientPaidUnPaid patientPaidUnPaid = new PatientPaidUnPaid();
            patientPaidUnPaid.setUnpaid((float) treatmentPlanService.getAllUnpaidTP(pid,false).stream().mapToDouble(x -> x.getPrice())
                    .sum());
            patientPaidUnPaid.setPaid((float) treatmentPlanService.getAllUnpaidTP(pid,true).stream().mapToDouble(x -> x.getPrice())
                    .sum());
            return ResponseEntity.ok().body(patientPaidUnPaid);
    }
}



@Data
class SafeParam {
    float AllExpenses ;
    float AllRevenue;
    float AllUnpaid;
}


@Data
class date {
    Date date;
}

class loDate {
    LocalDate date ;
}


@Data
class DateAndSafeName{
    Date d;
    String safeName;
}


@Data
class LocalDateAndSafeName {
        LocalDate d;
        String safeName;
}

@Data
class PatientPaidUnPaid{
    float paid;
    float unpaid;
}
