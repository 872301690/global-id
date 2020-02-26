package com.verbort;

import com.verbort.bean.Id;
import com.verbort.service.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IdController {
    
    @Autowired
    @Qualifier("ipIdService")
    private IdService idService;
    
    @RequestMapping("/genId")
    public Long genId(){
        return idService.genId();
    }

    @RequestMapping("/expId")
    public Id expId(Long id){
        return idService.expId(id);
    }
    @RequestMapping("/makeId")
    public Long makeId(@RequestParam(name = "machineId", required = false)Long machineId,
                       @RequestParam(name = "seq"  )Long seq,
                       @RequestParam(name = "time" )Long time,
                       @RequestParam(name ="genMethod" ,required = false)Long genMethod,
                       @RequestParam(name = "type" ,required = false)Long type,
                       @RequestParam(name = "version" ,required = false)Long version){

        Long madeId = null;
        if (time == -1 || seq == -1)
            throw new IllegalArgumentException(
                    "Both time and seq are required.");
        else if (version == -1) {
            if (type == -1) {
                if (genMethod == -1) {
                    if (machineId == -1) {
                        madeId = idService.makeId(time, seq);
                    } else {
                        madeId = idService.makeId(machineId, time, seq);
                    }
                } else {
                    madeId = idService.makeId(genMethod, machineId, time, seq);
                }
            } else {
                madeId = idService.makeId(type, genMethod, machineId, time, seq);
            }
        } else {
            madeId = idService.makeId(version, type, genMethod, time,
                    seq, machineId);
        }
        return madeId;
    }
    
}
