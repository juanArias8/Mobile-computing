from flask import Flask
from flask import jsonify
from flask import request
from pymongo import MongoClient

import controllers.database_controller as db_controller

app = Flask(__name__)

# Create mongo client
mongo_client = MongoClient('localhost', 27017)

# Connect to database RestaurantDB
restaurant_db = mongo_client.RestaurantDB

# Connect to users collection
users_collection = restaurant_db.users

# Connect to drinks collection
drinks_collection = restaurant_db.drinks

# Connect to plates collection
plates_collection = restaurant_db.plates

# Define URL folder to save images
# main_folder = os.path.realpath(__file__).replace('\\', '/').split('/')[0: -1]
# user_images = '/'.join(main_folder) + '/images/users/'
# drinks_images = '/'.join(main_folder) + '/images/drinks/'
# plates_images = '/'.join(main_folder) + '/images/plates/'


# GET
@app.route('/')
def hello_world():
    """
    :return: JSON: {success: success, "message": message}
    """
    success = True
    message = 'Restaurant App index'
    # print(user_images)
    # print(drinks_images)
    # print(plates_images)
    return jsonify({
        "success": success,
        "message": message
    })


# POST
@app.route('/login', methods=['POST'])
def login():
    """
    :param: JSON: {"email": email, "password": password}
    :return: JSON: {success: success, "message": message}
    """
    json = request.get_json()
    success, message = db_controller.search_user(users_collection, json)
    return jsonify({
        "success": success,
        "message": message
    })


# POST
@app.route('/singup', methods=['POST'])
def singup():
    """
    :param: JSON: {
                    "username": username,
                    "email": email,
                    "password": password,
                    "status": status,
                    "photo": photo
              }
    :return: JSON: {success: success, "message": message}
    """
    json = request.get_json()
    # photo = request.files['photo']
    #
    # photo_name = photo.filename
    # url_photo = user_images.join(photo_name)
    # photo.save(url_photo)

    success, message = db_controller.insert_user(users_collection, json)
    return jsonify({
        "success": success,
        "message": message
    })


# GET
@app.route('/data/plates')
def get_all_plates():
    """
    :param:
    :return: JSON: {
                   "name": name,
                   "price": price,
                   "photo": photo,
             }
    """
    success, message = db_controller.get_plates(plates_collection)
    return jsonify({
        "success": success,
        "message": message
    })


# GET
@app.route('/data/drinks')
def get_all_drinks():
    """
    :param:
    :return: JSON: {
                   "name": name,
                   "price": price,
                   "photo": photo,
             }
    """
    success, message = db_controller.get_drinks(drinks_collection)
    return jsonify({
        "success": success,
        "message": message
    })


# POST
@app.route('/data/plates', methods=['POST'])
def save_plate():
    """
    :param: JSON: {
                    "name": name,
                    "kind": kind,
                    "price": price,
                    "preparation_time": preparation_time,
                    "photo": photo
              }
    :return: JSON: {success: success, "message": message}
    """
    json = request.get_json()
    success, message = db_controller.insert_plate(plates_collection, json)
    return jsonify({
        "success": success,
        "message": message
    })


# POST
@app.route('/data/drinks', methods=['POST'])
def save_drink():
    """
    :param: JSON: {
                    "name": name,
                    "price": price,
                    "photo": photo
              }
    :return: JSON: {success: success, "message": message}
    """
    json = request.get_json()
    success, message = db_controller.insert_drink(drinks_collection, json)
    return jsonify({
        "success": success,
        "message": message
    })


# GET ERROR
@app.errorhandler(404)
def page_not_found(error):
    """
    :param error:
    :return: JSON: {success: success, "message": message}
    """
    success = False
    message = str(error)

    return jsonify({
        "success": success,
        "message": message
    }), 404


# GET ERROR
@app.errorhandler(500)
def server_error(error):
    """
    :param error:
    :return: JSON: {success: success, "message": message}
    """
    success = False
    message = str(error)

    return jsonify({
        "success": success,
        "message": message
    }), 500


if __name__ == '__main__':
    app.run(host="127.0.0.1", port=5000)
