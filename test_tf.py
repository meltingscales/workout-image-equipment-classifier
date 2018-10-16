import tensorflow as tf

a = tf.truncated_normal([16, 128, 128, 3])
sess = tf.Session()

sess.run(tf.initialize_all_variables())
res = sess.run(tf.shape(a))
print(res)

classes = ['sneakers', 'boxing gloves']
num_classes = len(classes)

train_path = 'data/downloaded_images'