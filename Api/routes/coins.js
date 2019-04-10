var express = require('express');
var router = express.Router();
var coinC= require('../controller/coinController');

/* GET users listing. */
router.get('/', coinC.getAll);

router.post('/', coinC.registrar);

router.get('/:id', coinC.buscarporId);

router.get('/name/:name', coinC.buscarporName);

router.get('/country/:country', coinC.buscarporCountry);

router.get('/year/:year', coinC.buscarporYear);

router.get('/isAvaliable/:isAvaliable', coinC.buscarporDisponibilidad);

//router.put('/:id', coinC.actualizar);

router.delete('/:id', coinC.deletear);

module.exports = router;
