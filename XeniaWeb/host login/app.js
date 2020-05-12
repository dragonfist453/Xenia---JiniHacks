const express = require('express')
const app = express()
const path = require('path')
const mongo = require('mongodb')
const bodyParser = require('body-parser')
const bcrypt = require('bcrypt')
const qr = require('qr-image')

//Stuff required for the app
app.use(express.static(__dirname))
app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())
app.set('view engine', 'ejs')

//Define Mongo client for access of database
var MongoClient = mongo.MongoClient;
var url = "mongodb://localhost:27017/mydb";

//Open index page
app.get('/', (req, res) => {
    res.render('/index.html')
})

//Login for a hotel
app.post('/login_hotel', (req, response) => {
    MongoClient.connect(url, function(err, db) {
        if (err) {
            console.log("Database connection failed!")
            return false
        }
        var dbo = db.db("test");
        credentials = req.body;
        input_username = { username: credentials.username };
        input_password = credentials.password;
        dbo.collection("hotels").findOne(input_username, function(err, res) {
            if (err) {
                res = { password: '' }
            }
            if (res == null) {
                res = { password: '' }
            }
            bcrypt.compare(input_password, res.password, function(err, res) {
                if (res == true) {
                    response.redirect('/hotel_menu.html')
                } else {
                    response.redirect('/')
                }
            });
            db.close();
        });
    });
})

//Login for a user
app.post('/login_user', (req, response) => {
    MongoClient.connect(url, function(err, db) {
        if (err) {
            console.log("Database connection failed!")
            return false
        }
        var dbo = db.db("test");
        credentials = req.body;
        input_email = { email: credentials.email };
        input_password = credentials.password;
        dbo.collection("users").findOne(input_email, function(err, res) {
            if (err) {
                response.send({ "authorised": false })
            }
            bcrypt.compare(input_password, res.password, function(err, res) {
                return_json = { "authorised": res }
                response.send(JSON.stringify(return_json))
            });
            db.close();
        });
    });
})

//Register for hotel
app.post('/register_hotel', (req, res) => {
    MongoClient.connect(url, function(err, db) {
        if (err) {
            console.log("Database connection failed!")
            return false
        }
        var dbo = db.db("test");
        credentials = req.body;
        input_password = credentials.password;
        delete credentials.confirm_password;
        var salt = bcrypt.genSaltSync(10);
        credentials.password = bcrypt.hashSync(input_password, salt);
        dbo.collection("hotels").insertOne(credentials, function(err, res) {
            if (err) {
                console.log("Error in inserting document");
                return false;
            }
            db.close();
            return true;
        });
    });
    res.redirect('/confirmation.html')
})

//Register for user
app.post('/register_user', (req, response) => {
    MongoClient.connect(url, function(err, db) {
        if (err) throw err;
        var dbo = db.db("test");
        credentials = req.body;
        input_password = credentials.password;
        var salt = bcrypt.genSaltSync(10);
        credentials.password = bcrypt.hashSync(input_password, salt);
        dbo.collection("users").insertOne(credentials, function(err, res) {
            if (err) {
                console.log("Error in inserting document");
                response.send('{ "status": false }');
            }
            db.close();
            response.send('{ "status": true }');
        });
    });
})

//Output a json of hotels dataset
app.post('/get_hotels', (req, response) => {
    MongoClient.connect(url, function(err, db) {
        if (err) throw err;
        var dbo = db.db("test");
        dbo.collection("hotels").find({}).toArray(function(err, result) {
            if (err) throw err;
            db.close();
            response.send(result)
        });
    })
})

//Output a json of hotels for a Web App
app.get('/get_hotels_webapp', (req, response) => {
        MongoClient.connect(url, function(err, db) {
            if (err) throw err;
            var dbo = db.db("test");
            dbo.collection("hotels").find({}).toArray(function(err, result) {
                if (err) throw err;
                db.close();
                response.send(result)
            });
        })
    })
    //Output a json of users
app.get('/get_users', (req, response) => {
    MongoClient.connect(url, function(err, db) {
        if (err) throw err;
        var dbo = db.db("test");
        dbo.collection("users").find({}).toArray(function(err, result) {
            if (err) throw err;
            db.close();
            response.send(result)
        });
    })
})

//Generate QR Code
app.get('/qrcode', (req, res, next) => {
    let qr_txt = req.query.qr;
    var qr_png = qr.imageSync(qr_txt, { type: 'png' })
    res.send(qr_png)
});

//Make transaction
app.post('/make_transaction', (req, response) => {
    MongoClient.connect(url, function(err, db) {
        if (err) throw err;
        var dbo = db.db("test");
        user_id = req.body.user_id;
        hotel_id = req.body.hotel_id;
        booking = { "user_id": user_id, "hotel_id": hotel_id }
        dbo.collection("bookings").insertOne(booking, function(err, res) {
            if (err) {
                console.log("Error in inserting document");
                response.send({ "status": false });
            }
            db.close();
            response.send({ "status": true });
        });
    });
})

//Get user data
app.post('/get_userdata', (req, response) => {
    MongoClient.connect(url, function(err, db) {
        if (err) throw err;
        var dbo = db.db("test");
        dbo.collection("users").findOne(req.body, function(err, res) {
            if (err) {
                console.log("Error in getting document");
                response.send({});
            }
            db.close();
            response.send(res);
        });
    });
})

//Get hotel data
app.get('/get_hoteldata', (req, response) => {
    MongoClient.connect(url, function(err, db) {
        if (err) throw err;
        var dbo = db.db("test");
        dbo.collection("hotels").findOne(req.body, function(err, res) {
            if (err) {
                console.log("Error in inserting document");
                response.send(res);
            }
            db.close();
            response.send({});
        });
    });
})

//Output a JSON of Users with food requested
app.get('/get_users_food', (req, response) => {
        MongoClient.connect(url, function(err, db) {
            if (err) throw err;
            var dbo = db.db("test");
            dbo.collection("users").find({ food: true }).toArray(function(err, result) {
                if (err) throw err;
                db.close();
                response.send(result)
            });
        })
    })
    //Outputs a list of users with room service requests
app.get('/get_users_rs', (req, response) => {
        MongoClient.connect(url, function(err, db) {
            if (err) throw err;
            var dbo = db.db("test");
            dbo.collection("users").find({ roomservice: true }).toArray(function(err, result) {
                if (err) throw err;
                db.close();
                response.send(result)
            });
        })
    })
    //Output a list of Users requesting a wake up call
app.get('/get_users_wakeupcall', (req, response) => {
    MongoClient.connect(url, function(err, db) {
        if (err) throw err;
        var dbo = db.db("test");
        dbo.collection("users").find({ wakeupcall: { $exists: true } }).toArray(function(err, result) {
            if (err) throw err;
            db.close();
            response.send(result)
        });
    })
})

app.post('/update_users', (req, response) => {
    MongoClient.connect(url, function(err, db) {
        if (err) throw err;
        var dbo = db.db("test");
        email = req.body.email;
        console.log(req.body)
        updateStr = { $set: req.body };
        dbo.collection("users").update({ "email": email }, updateStr, function(req, res) {
            if (!{ $exists: res }) {
                console.log("Error occurred when updating database");
                response.send({ 'status': false })
            }
            db.close();
            response.send({ 'status': true })
        });
    })
})
app.listen(5000, '0.0.0.0', () => console.log("Server running"))