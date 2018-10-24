# README

This is a readme. Please read me.

# How do I get set up?

Note: All steps are to take place inside this cloned/downloaded repository.

1. Install Python and use Pipenv.

  1. Install Python 3.6. Make sure pip is installed.
  2. Run `pip install pipenv`. This installs pipenv, a dependency-management tool.
  3. Run `pipenv install`. This installs the packages required to download images and train the neural net.

2. Download the training images.

  1. Run `pipenv run python download_images.py`
    
     This will save them to `/data/downloaded_images/`.

3. Train an existing neural net.

  1. Run `retrain.[bat|sh]`.

4. Make the neural net small as to fit on phones.

  1. Run `retrained_to_mobile_toco.[bat|sh]`.

     This will make two files in the `retrained_model` folder:
     
     `optimized_graph.lite`, and `output_labels.txt`.
     
     These are ready to be put onto a phone.

5. Android Studio magic.

   1. Copy these files:

          retrained_model/optimized_graph.lite
          retrained_model/output_labels.txt
          
      into `\android_apps\tensorflow_for_poets_tflite\app\src\main\assets\`.
      
      The build will now not fail due to `assert` statements to check for those files existing.


### TODO
* Configuration
* Dependencies
* Database configuration
* How to run tests
* Deployment instructions

# Contribution guidelines

* Writing tests
* Code review
* Other guidelines

# Who do I talk to?

* Repo owner or admin
* Other community or team contact
