const mongoose= require('mongoose');
const coinModel= require('../models/Coin');
var coinC={};

coinC.getAll=(function(req, res){
    coinModel.find({}, function(err, datos){
        if(err){
            res.status(500).json({status:500, err});
        }else{
            console.log('Data obtenidad con exito')
            res.status(200).json({status:200, datos});
        }
    })
})

coinC.registrar=(function(req, res){
    var obj= new coinModel({
        name: req.body.name,
        country: req.body.country,
        value:req.body.value ,
        value_us: req.body.value_us,
        year: req.body.year,
        review: req.body.review,
        isAvaliable:req.body.isAvaliable,
        img: req.body.img,
        imgBanderaPais: req.body.imgBanderaPais
    });
    obj.save(function(err){
        if(err){
            res.status(500).json({status:500, err})
        }else{
            console.log('Se registro con exito');
            res.status(200).json({status:200, obj});
        }
    })

})
coinC.buscarporId= (function(req, res){
    //console.log(req);
    var id= req.params.id;
    coinModel.findById(id, function(err, coin){
        if(err || coin==null){
            res.status(500).json({
                msj:'No fue encontrado la moneda',
                err,
                status:500
            })
        }else{
            res.status(200).json({status:200, coin});
        }
    })
});

coinC.buscarporName= (function(req, res){
    console.log(req)
    console.log(req.params.name)
    coinModel.find({name: req.params.name}, function(err, coin){
        if(err || coin==null){
            res.status(500).json({
                msj:'No fue encontrado la moneda',
                err,
                status:500
            })
        }else{
            res.status(200).json({status:200, coin});
        }
    })
});

coinC.buscarporCountry= (function(req, res){
    //console.log(req);
    var country= req.params.country;
    coinModel.findById(country, function(err, coin){
        if(err || coin==null){
            res.status(500).json({
                msj:'No fue encontrado la moneda',
                err,
                status:500
            })
        }else{
            res.status(200).json({status:200, coin});
        }
    })
});

coinC.buscarporYear= (function(req, res){
    //console.log(req);
    var year= req.params.year;
    coinModel.findById(year, function(err, coin){
        if(err || coin==null){
            res.status(500).json({
                msj:'No fue encontrado la moneda',
                err,
                status:500
            })
        }else{
            res.status(200).json({status:200, coin});
        }
    })
});

coinC.buscarporDisponibilidad= (function(req, res){
    //console.log(req);
    var isAvaliable= req.params.isAvaliable;
    coinModel.findById(isAvaliable, function(err, coin){
        if(err || coin==null){
            res.status(500).json({
                msj:'No fue/fueron encontrado/as la moneda',
                err,
                status:500
            })
        }else{  
            res.status(200).json({status:200, coin});
        }
    })
});

/*
coinC.actualizar= (function(req, res){
    //console.log(req);
    var id= req.params.id;
    var actualizado={
        Titulo: req.body.titulo,
        Desarrolladora: req.body.desarrolladora,
        Ign: req.body.ign
    }
    coinModel.findByIdAndUpdate(id, actualizado, function(err){
        if(err){
            res.status(500).json({msj:'No se pudo actualizar', err, status:500});
        }else{
            res.status(200).json({status:200, actualizado});
        }
    })
});
*/
coinC.deletear= (function(req, res){
    //console.log(req);
    var id= req.params.id;

    coinModel.findByIdAndDelete(id, function(err, eliminado){
        if(err){
            res.status(500).json({msj:'No se pudo eliminar ', err, status:500});
        }else{
            res.status(200).json({status:200, eliminado});
        }
    })
});


module.exports=coinC;