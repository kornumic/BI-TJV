package cz.cvut.fit.tjv.semestral.api;

import cz.cvut.fit.tjv.semestral.api.model.EmployeeDto;
import cz.cvut.fit.tjv.semestral.api.model.converter.EmployeeToDtoConverter;
import cz.cvut.fit.tjv.semestral.api.model.converter.EmployeeToEntityConverter;
import cz.cvut.fit.tjv.semestral.business.EmployeeService;
import cz.cvut.fit.tjv.semestral.business.EntityStateException;
import cz.cvut.fit.tjv.semestral.domain.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController extends AbstractCrudController<Employee, EmployeeDto, Long> {
    public EmployeeController(EmployeeService service, EmployeeToDtoConverter toDtoConverter, EmployeeToEntityConverter toEntityConverter){
        super(service, toDtoConverter, toEntityConverter);
    }

    @Override
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody EmployeeDto e) {
        try{
            return ResponseEntity.ok(toDtoConverter.apply(service.create(toEntityConverter.apply(e))));
        }
        catch (EntityStateException exc){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        catch (NullPointerException exc){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public Collection<EmployeeDto> readAll(@RequestParam Optional<Boolean> assignable) {
        if(assignable.isPresent() && assignable.get())
            return ((EmployeeService) service).readAllAssignable().stream().map(toDtoConverter).toList();
        return super.readAll();
    }
}
