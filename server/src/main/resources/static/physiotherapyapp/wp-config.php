<?php

/**

 * The base configuration for WordPress

 *

 * The wp-config.php creation script uses this file during the

 * installation. You don't have to use the web site, you can

 * copy this file to "wp-config.php" and fill in the values.

 *

 * This file contains the following configurations:

 *

 * * MySQL settings

 * * Secret keys

 * * Database table prefix

 * * ABSPATH

 *

 * @link https://wordpress.org/support/article/editing-wp-config-php/

 *

 * @package WordPress

 */


// ** MySQL settings - You can get this info from your web host ** //

/** The name of the database for WordPress */

define( 'DB_NAME', '' );


/** MySQL database username */

define( 'DB_USER', '' );


/** MySQL database password */

define( 'DB_PASSWORD', '' );


/** MySQL hostname */

define( 'DB_HOST', '' );


/** Database Charset to use in creating database tables. */

define( 'DB_CHARSET', 'utf8' );


/** The Database Collate type. Don't change this if in doubt. */

define( 'DB_COLLATE', '' );


/**#@+

 * Authentication Unique Keys and Salts.

 *

 * Change these to different unique phrases!

 * You can generate these using the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}

 * You can change these at any point in time to invalidate all existing cookies. This will force all users to have to log in again.

 *

 * @since 2.6.0

 */

define('AUTH_KEY',         '12e8d32dbc347fdfa4960966b17e0d4f6ce971b11c0f54f1b574027352b9e942');

define('SECURE_AUTH_KEY',  '8db2ff4c60eaf4f8904795d614767b978f4131e91a46deca2f1667957a43efd9');

define('LOGGED_IN_KEY',    'c481cbd3bf357520b596ef397b0e53df96910a729f031682377e374e06805405');

define('NONCE_KEY',        '05b2ce2527ae27a8a1f324f944b5778e69137fe9360ca5036af98acf4b3739ff');

define('AUTH_SALT',        'be504fc30f2abf05796a70c41d3f5adac608deb44e63baf2bda383be60608a23');

define('SECURE_AUTH_SALT', 'fbe1d3cd59eecf1be5be0a517d57c001fdccea4a185746854efcc1b007ef8b8a');

define('LOGGED_IN_SALT',   '39d08e66933b8d69eb13d13faa0f1b8c2082ddede1bb771b7ece96367d0600bb');

define('NONCE_SALT',       '05003aed00f73a62c5bf86755f9861066baa087ef6d3c009e83814796d6141d0');


/**#@-*/


/**

 * WordPress Database Table prefix.

 *

 * You can have multiple installations in one database if you give each

 * a unique prefix. Only numbers, letters, and underscores please!

 */

$table_prefix = 'wp_';


/**

 * For developers: WordPress debugging mode.

 *

 * Change this to true to enable the display of notices during development.

 * It is strongly recommended that plugin and theme developers use WP_DEBUG

 * in their development environments.

 *

 * For information on other constants that can be used for debugging,

 * visit the documentation.

 *

 * @link https://wordpress.org/support/article/debugging-in-wordpress/

 */

define( 'WP_DEBUG', false );


/* That's all, stop editing! Happy publishing. */

/**

 * The WP_SITEURL and WP_HOME options are configured to access from any hostname or IP address.

 * If you want to access only from an specific domain, you can modify them. For example:

 *  define('WP_HOME','http://example.com');

 *  define('WP_SITEURL','http://example.com');

 *

*/


if ( defined( 'WP_CLI' ) ) {

    $_SERVER['HTTP_HOST'] = 'localhost';

}


define('WP_SITEURL', 'http://' . $_SERVER['HTTP_HOST'] . '/wordpress');

define('WP_HOME', 'http://' . $_SERVER['HTTP_HOST'] . '/wordpress');



/** Absolute path to the WordPress directory. */

if ( ! defined( 'ABSPATH' ) ) {

	define( 'ABSPATH', __DIR__ . '/' );

}


/** Sets up WordPress vars and included files. */

require_once ABSPATH . 'wp-settings.php';


define('WP_TEMP_DIR', 'C:\Bitnami\wordpress-5.5-0/apps/wordpress/tmp');



//  Disable pingback.ping xmlrpc method to prevent Wordpress from participating in DDoS attacks

//  More info at: https://docs.bitnami.com/general/apps/wordpress/troubleshooting/xmlrpc-and-pingback/


if ( !defined( 'WP_CLI' ) ) {

    // remove x-pingback HTTP header

    add_filter('wp_headers', function($headers) {

        unset($headers['X-Pingback']);

        return $headers;

    });

    // disable pingbacks

    add_filter( 'xmlrpc_methods', function( $methods ) {

            unset( $methods['pingback.ping'] );

            return $methods;

    });

    add_filter( 'auto_update_translation', '__return_false' );

}

