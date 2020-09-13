<?php
/**
 * Plugin Name: Charts and Graphs for Elementor 
 * Description: Elementor addons to create beautiful, interactive charts and graphs.
 * Plugin URI:  https://redlettuce.com/charts-and-graphs-for-elementor
 * Version:     1.2.1
 * Author:      RedLettuce Plugins
 * Author URI:  https://redlettuce.com
 * Text Domain: elementor_charts_graphs
 */

if ( ! defined( 'ABSPATH' ) ) exit; // Exit if accessed directly

/**
 * Main Elementor Charts Graphs Class
 *
 * The init class that runs the Charts Graphs plugin.
 * Intended To make sure that the plugin's minimum requirements are met.
 *
 * You should only modify the constants to match your plugin's needs.
 *
 * Any custom code should go inside Plugin Class in the plugin.php file.
 * @since 1.2.0
 */
final class Elementor_Charts_Graphs {

	/**
	 * Plugin Version
	 *
	 * @since 1.2.0
	 * @var string The plugin version.
	 */
	const VERSION = '1.2.1';

	/**
	 * Minimum Elementor Version
	 *
	 * @since 1.2.0
	 * @var string Minimum Elementor version required to run the plugin.
	 */
	const MINIMUM_ELEMENTOR_VERSION = '2.0.0';

	/**
	 * Minimum PHP Version
	 *
	 * @since 1.2.0
	 * @var string Minimum PHP version required to run the plugin.
	 */
	const MINIMUM_PHP_VERSION = '7.0';

	/**
	 * Constructor
	 *
	 * @since 1.0.0
	 * @access public
	 */
	public function __construct() {

		// Load translation
		add_action( 'init', array( $this, 'i18n' ) );

		// Init Plugin
		add_action( 'plugins_loaded', array( $this, 'init' ) );

		register_activation_hook( __FILE__, array( $this, 'void_activation_time') );

		add_action( 'admin_init', array( $this, 'void_check_installation_time') );

		add_action( 'admin_init', array( $this, 'void_spare_me'), 5 );
	}

	/**
	 * Load Textdomain
	 *
	 * Load plugin localization files.
	 * Fired by `init` action hook.
	 *
	 * @since 1.2.0
	 * @access public
	 */
	public function i18n() {
		load_plugin_textdomain( 'elementor_charts_graphs' );
	}

	/**
	 * Initialize the plugin
	 *
	 * Validates that Elementor is already loaded.
	 * Checks for basic plugin requirements, if one check fail don't continue,
	 * if all check have passed include the plugin class.
	 *
	 * Fired by `plugins_loaded` action hook.
	 *
	 * @since 1.2.0
	 * @access public
	 */
	public function init() {

		// Check if Elementor installed and activated
		if ( ! did_action( 'elementor/loaded' ) ) {
			add_action( 'admin_notices', array( $this, 'admin_notice_missing_main_plugin' ) );
			return;
		}

		// Check for required Elementor version
		if ( ! version_compare( ELEMENTOR_VERSION, self::MINIMUM_ELEMENTOR_VERSION, '>=' ) ) {
			add_action( 'admin_notices', array( $this, 'admin_notice_minimum_elementor_version' ) );
			return;
		}

		// Check for required PHP version
		if ( version_compare( PHP_VERSION, self::MINIMUM_PHP_VERSION, '<' ) ) {
			add_action( 'admin_notices', array( $this, 'admin_notice_minimum_php_version' ) );
			return;
		}

		// Once we get here, We have passed all validation checks so we can safely include our plugin
		require_once( 'plugin.php' );
	}

	/**
	 * Admin notice
	 *
	 * Warning when the site doesn't have Elementor installed or activated.
	 *
	 * @since 1.0.0
	 * @access public
	 */
	public function admin_notice_missing_main_plugin() {
		if ( isset( $_GET['activate'] ) ) {
			unset( $_GET['activate'] );
		}

		$message = sprintf(
			/* translators: 1: Plugin name 2: Elementor */
			esc_html__( '"%1$s" requires "%2$s" to be installed and activated.', 'elementor_charts_graphs' ),
			'<strong>' . esc_html__( 'Elementor Charts Graphs', 'elementor_charts_graphs' ) . '</strong>',
			'<strong>' . esc_html__( 'Elementor', 'elementor_charts_graphs' ) . '</strong>'
		);

		printf( '<div class="notice notice-warning is-dismissible"><p>%1$s</p></div>', $message );
	}

	/**
	 * Admin notice
	 *
	 * Warning when the site doesn't have a minimum required Elementor version.
	 *
	 * @since 1.0.0
	 * @access public
	 */
	public function admin_notice_minimum_elementor_version() {
		if ( isset( $_GET['activate'] ) ) {
			unset( $_GET['activate'] );
		}

		$message = sprintf(
			/* translators: 1: Plugin name 2: Elementor 3: Required Elementor version */
			esc_html__( '"%1$s" requires "%2$s" version %3$s or greater.', 'elementor_charts_graphs' ),
			'<strong>' . esc_html__( 'Elementor Charts Graphs', 'elementor_charts_graphs' ) . '</strong>',
			'<strong>' . esc_html__( 'Elementor', 'elementor_charts_graphs' ) . '</strong>',
			self::MINIMUM_ELEMENTOR_VERSION
		);

		printf( '<div class="notice notice-warning is-dismissible"><p>%1$s</p></div>', $message );
	}

	/**
	 * Admin notice
	 *
	 * Warning when the site doesn't have a minimum required PHP version.
	 *
	 * @since 1.0.0
	 * @access public
	 */
	public function admin_notice_minimum_php_version() {
		if ( isset( $_GET['activate'] ) ) {
			unset( $_GET['activate'] );
		}

		$message = sprintf(
			/* translators: 1: Plugin name 2: PHP 3: Required PHP version */
			esc_html__( '"%1$s" requires "%2$s" version %3$s or greater.', 'elementor_charts_graphs' ),
			'<strong>' . esc_html__( 'Elementor Charts Graphs', 'elementor_charts_graphs' ) . '</strong>',
			'<strong>' . esc_html__( 'PHP', 'elementor_charts_graphs' ) . '</strong>',
			self::MINIMUM_PHP_VERSION
		);

		printf( '<div class="notice notice-warning is-dismissible"><p>%1$s</p></div>', $message );
	}

	// add plugin activation time
	public function void_activation_time(){
		$get_activation_time = strtotime("now");
		add_option('pdf_elementor_activation_time', $get_activation_time );
	}


	//check if review notice should be shown or not
	public function void_check_installation_time() {   
		
		$install_date = get_option( 'pdf_elementor_activation_time' );
		$past_date = strtotime( 'now' ); //-3 days
	
		if ( $past_date >= $install_date ) {
	
			add_action( 'admin_notices', array( $this, 'void_display_admin_notice') );
	
		}

	}

	/**
	* Display Admin Notice, asking for a review
	**/
	public function void_display_admin_notice() {
		$dont_disturb = esc_url( get_admin_url() . '?spare_me=1' );
		$plugin_info = get_plugin_data( __FILE__ , true, true ); 
		     
		$reviewurl = esc_url( 'https://wordpress.org/support/plugin/'. sanitize_title( $plugin_info['Name'] ) . '/reviews/' );
		if( !get_option('void_spare_me') ){
			printf(__('<div class="notice notice-success" style="padding: 10px;">You have been using <b> %s </b> for a while. We hope you liked it! Please give us a quick rating, it works as a boost for us to keep working on the plugin!<br><div class="void-review-btn"><a href="%s" style="margin-top: 10px; display: inline-block; margin-right: 5px;" class="button button-primary" target=
			"_blank">Rate Now!</a><a href="%s" style="margin-top: 10px; display: inline-block; margin-right: 5px;" class="button button-secondary">Already Done !</a></div></div>', $plugin_info['TextDomain']), $plugin_info['Name'], $reviewurl, $dont_disturb );
		}
	}

	// remove the notice for the user if review already done or if the user does not want to
	public function void_spare_me(){    
		if( isset( $_GET['spare_me'] ) && !empty( $_GET['spare_me'] ) ){
			$spare_me = $_GET['spare_me'];
			if( $spare_me == 1 ){
				add_option( 'void_spare_me' , TRUE );
			}
		}
	}

}

// Instantiate Elementor_Charts_Graphs.
new Elementor_Charts_Graphs();
