var mongoose= require('mongoose');
var Schema = mongoose.Schema;

const coinSchema= new Schema({
    name:{
        type: String
    },
    country:{
        type:String
    },
    value:{
        type: String,
        default: "0.00"
    },
    value_us:{
        type: String,
        default: "0.00"
    },
    year:{
        type: String,
        default: "2000"
    },
    review:{
        type: String,
        default: "Moneda X"
    },
    isAvaliable:{
        type: Boolean,
        default:true
    },
    img:{   
        type: String,
        default: "Imagen url no disponible"
    },
    imgBanderaPais:{
        type: String,
        default: "Url de bandera no disponible"
    }
})
module.exports= mongoose.model('coin',coinSchema);